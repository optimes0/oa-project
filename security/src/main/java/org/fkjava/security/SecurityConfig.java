package org.fkjava.security;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.fkjava.menu.domain.Menu;
import org.fkjava.menu.service.MenuService;
import org.fkjava.security.domain.UserDetails;
import org.fkjava.security.interceptor.UserHolderInterceptor;
import org.fkjava.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@ComponentScan("org.fkjava")
@EnableJpaRepositories
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{
	
	private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	private SecurityService securityService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MenuService menuService;
	@Autowired
	private ObjectMapper objectMapper;
	// 自定义AuthenticationProvider，不隐藏【用户未找到的异常】
 	// Spring Security会默认自动创建AuthenticationProvider
 	// 但是如果开发者自己提供了，那么就不会自动创建
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//不要调用supersuper.configure(auth)方法
		DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
		dap.setHideUserNotFoundExceptions(false);
		dap.setUserDetailsService(securityService);
		dap.setPasswordEncoder(passwordEncoder);
		
		auth.authenticationProvider(dap);
	}

	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//把拦截器添加进spring里面，拦截根目录以及根目录下的子目录
		registry.addInterceptor(new UserHolderInterceptor()).addPathPatterns("/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String loginPage = "/security/login";
		
		SimpleUrlAuthenticationFailureHandler fh = new SimpleUrlAuthenticationFailureHandler(loginPage + "?error") {

			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				//把登录名放入session里面
				request.getSession().setAttribute("loginName", request.getParameter("loginName"));
				super.onAuthenticationFailure(request, response, exception);
			}
			
		};
		
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler() {

			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws ServletException, IOException {
				HttpSession session = request.getSession();
				UserDetails ud = (UserDetails)authentication.getPrincipal();
				
				//获取所有用户的url
				Set<String> urls = menuService.findMyUrls(ud.getUserId());
				//获取用户的左侧菜单树
				List<Menu> menus = menuService.findMyMenus(ud.getUserId());
				//转换所有菜单为json，避免每次访问生成一次
				String menuJson;
				try {
					menuJson = objectMapper.writeValueAsString(menus);
				}catch (JsonProcessingException e) {
					LOG.error("无法把用户菜单转换为json", e.getLocalizedMessage(),e);
					menuJson = "[]";
				}
				
				//把用户菜单存到Session里面
				session.setAttribute("menuJson", menuJson);
				session.setAttribute("urls", urls);
				super.onAuthenticationSuccess(request, response, authentication);
			}
			
		};
		
		http.authorizeRequests()//验证请求
			.antMatchers(loginPage,"/css/**","/js/**","/webjars/**","/static/**")
			.permitAll()//不做访问判断
			.anyRequest()//所有请求
			.authenticated()//授权后才能访问
			.and()//并且
			.formLogin()//使用表单登录
			.loginPage(loginPage)//登录页面位置，默认是/login
			.loginProcessingUrl("/security/do-login")//处理登录请求的url
			.successHandler(successHandler)//登录成功后的处理器
			.usernameParameter("loginName")//登录名的参数名
			.passwordParameter("password")//登录密码的参数名
			.failureHandler(fh)
			.and().logout()//配置登出
			.logoutUrl("/security/do-logout")//登出的url
			//.and().httpBasic();
			.and().csrf();//启用防跨站攻击
		
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//系统自动把静态文件的根目录映射到，/static, /public, /resources里面
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//动态注册URL和视图的映射关闭，解决控制器里几乎没有代码的问题
		registry.addViewController("/security/login")
				.setViewName("security/login");
		
		//设置首页
		registry.addViewController("/").setViewName("security/index");
		
	}

	public static void main(String[] args) {
		SpringApplication.run(SecurityConfig.class, args);
	}
}

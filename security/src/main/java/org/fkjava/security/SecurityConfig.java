package org.fkjava.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan("org.fkjava")
@EnableJpaRepositories
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{
		
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()//验证请求
			.antMatchers("/security/login","/css/**","/js/**","/webjars/**","/static/**")
			.permitAll()//不做访问判断
			.anyRequest()//所有请求
			.authenticated()//授权后才能访问
			.and()//并且
			.formLogin()//使用表单登录
			.loginPage("/security/login")//登录页面位置，默认是/login
			.loginProcessingUrl("/security/do-login")//处理登录请求的url
			.usernameParameter("loginName")//登录名的参数名
			.passwordParameter("password")//登录密码的参数名
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
	}

	public static void main(String[] args) {
		SpringApplication.run(SecurityConfig.class, args);
	}
}

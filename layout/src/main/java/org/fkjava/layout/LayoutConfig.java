package org.fkjava.layout;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;

import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan("org.fkjava")
@EnableJpaRepositories
public class LayoutConfig implements WebMvcConfigurer{
	
		//在spring里面增加自定义容器
		@Bean
		public FilterRegistrationBean<ConfigurableSiteMeshFilter> siteMeshFilter(){
			ConfigurableSiteMeshFilter csf = new ConfigurableSiteMeshFilter();
			FilterRegistrationBean<ConfigurableSiteMeshFilter> bean = new FilterRegistrationBean<>();
			//拦截所有的url
			bean.addUrlPatterns("/*");
			//把过滤器加入spring中
			bean.setFilter(csf);
			//激活异步请求支持
			bean.setAsyncSupported(true);
			//激活使用
			bean.setEnabled(true);
			//只处理来自浏览器的请求，其他不处理
			bean.setDispatcherTypes(DispatcherType.REQUEST);
			
			//初始化过滤参数
			Map<String, String> initParameters = new HashMap<>();
			//当是/*的请求时，使用/WEB-INF/layouts/main.jsp来装饰
			//当是/admin/*的请求时，使用/WEB-INF/layouts/admin.jsp来装饰
			initParameters.put("decoratorMappings", "/*=/WEB-INF/layouts/main.jsp\n"
					+ "/security/login=/WEB-INF/layouts/simple.jsp");
			
			bean.setInitParameters(initParameters);
			return bean;
		}
	
		public static void main(String[] args) {
			SpringApplication.run(LayoutConfig.class, args);
		}
}

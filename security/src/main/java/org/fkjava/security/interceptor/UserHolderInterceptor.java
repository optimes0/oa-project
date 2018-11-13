package org.fkjava.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fkjava.identity.domain.User;
import org.fkjava.identity.util.UserHolder;
import org.fkjava.security.domain.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UserHolderInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//Authentication是认证的意思，Principal是首要的
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails) {
			//如果principal有UserDetails，则new一个User放进UserHolder里面
			UserDetails ud = (UserDetails) principal;
			User user = new User();
			user.setId(ud.getUserId());
			user.setName(ud.getUsername());
			
			UserHolder.set(user);
			
			
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//把线程里的User清除	
		UserHolder.remove();
	}

	
	
}

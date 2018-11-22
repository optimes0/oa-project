package org.fkjava.security.service.impl;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

public class MyAccessControl {
	//日志文件
	private static final Logger LOG = LoggerFactory.getLogger(MyAccessControl.class);
	
	public boolean check(Authentication authentication,HttpServletRequest request) {
		HttpSession session = request.getSession();
		Set<String> urls = (Set<String>)session.getAttribute("urls");
		String contextPath = request.getContextPath();
		String requestUri = request.getRequestURI();
		if(!contextPath.isEmpty()) {
			//如果有contextPath，则截掉
			requestUri = requestUri.substring(contextPath.length());
		}
		for(String url : urls) {
			if(requestUri.equals(url)) {
				return true;
			}
			//url里面是包含了正则表达式的
			if(requestUri.matches(url)) {
				//匹配了也返回true
				return true;
			}
		}
		LOG.trace("访问被拒绝，访问的url:{},用户的url集合:{}",requestUri,urls);
		return false;
	}
}

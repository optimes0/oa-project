package org.fkjava.security.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.fkjava.identity.domain.User;
import org.fkjava.identity.service.IdentityService;
import org.fkjava.security.domain.UserDetails;
import org.fkjava.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private IdentityService identityService;
	
	@Override
	public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
		//到数据库里根据登录名查找用户信息
		Optional<User> op = this.identityService.findByLoginName(loginName);
		//没有user则抛出异常
		User user = op.orElseThrow(()->{
			return new UsernameNotFoundException("用户的登录名"+ loginName + "没有找到");
		});
		
		Collection<GrantedAuthority> authority = new HashSet<>();
		//获取所有的角色集合
		user.getRoles().forEach(role -> {
			//在角色的KEY前面加上ROLE_作为【已授权身份】
			//ROLE_是spring要求的
			GrantedAuthority ga = new SimpleGrantedAuthority("ROLE_"+role.getRoleKey());
			authority.add(ga);
		});
		UserDetails ud = new UserDetails(user, authority);
		return ud;
	}

}

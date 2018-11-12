package org.fkjava.security.domain;

import java.util.Collection;

import org.fkjava.identity.domain.User;
import org.springframework.security.core.GrantedAuthority;

public class UserDetails extends org.springframework.security.core.userdetails.User{

	
	private static final long serialVersionUID = 1L;
	/***
	 * 账户在数据库里的id
	 */
	private String userId;
	/***
	 * 数据库里的账户姓名
	 */
	private String name;
	/***
	 * 
	 * @param username 用户的姓名
	 * @param password 数据库加密后的密码
	 * @param enabled 是否激活
	 * @param accountNonExpired 账户是否未过期
	 * @param credentialsNonExpired 密码是否未过期
	 * @param accountNonLocked 账户是否未锁定
	 * @param authorities 角色集合
	 */
	private UserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		// TODO Auto-generated constructor stub
	}
	
	public UserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getLoginName(),user.getPassword(),
				user.getStatus() == User.Status.NORMAL,//正常
				user.getStatus() != User.Status.EXPIRED,//不过期
				user.getStatus() != User.Status.EXPIRED,
				user.getStatus() != User.Status.DISABLED,//不禁用
				authorities);
		this.userId = user.getId();
		this.name = user.getName();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}

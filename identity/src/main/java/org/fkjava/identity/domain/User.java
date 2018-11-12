package org.fkjava.identity.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="tb_user")
public class User {
	
	@Id
	@GenericGenerator(name="uuid2",strategy="uuid2")
	@GeneratedValue(generator="uuid2")
	@Column(length=36)
	private String id;
	
	@Column(length=20)
	private String name;
	
	//登录名是唯一的
	@Column(name="login_name",length=20,unique=true)
	private String loginName;
	
	//密码的长度要长一些，后面要进行加密
	@Column(length=255)
	private String password;

	@ManyToMany(fetch=FetchType.EAGER)//表示当查询user的时候把role也一并查询出来
	@JoinTable(name="tb_user_role",//指定中间表表名
				joinColumns= {@JoinColumn(name="user_id")},//指定user的外键名称
				inverseJoinColumns= {@JoinColumn(name="role_id")})//根据role找user的时候的role的外键名称
	private List<Role> roles;
	//过期时间，每次修改都需要把时间加上两个月
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredTime;
	
	//把枚举变量名称（STRING）存储到数据库
	//默认存储索引（ORDINAL）
	@Enumerated(EnumType.STRING)
	private Status status;
	
	//枚举内部类
	public static enum Status{
		/**
		 * 正常
		 */
		NORMAL,
		/**
		 * 过期
		 */
		EXPIRED,
		/**
		 * 禁用
		 */
		DISABLED
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public Status getStatus() {
		if(this.status == Status.DISABLED) {
			//如果状态是禁用则直接返回禁用
			return Status.DISABLED;
		}
		if(this.expiredTime != null 
				&& System.currentTimeMillis() >= this.expiredTime.getTime()) {
			//如果当前时间超过了或到了过期时间则返回过期
			return Status.EXPIRED;
		}
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
	
	
}

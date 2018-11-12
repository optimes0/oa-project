package org.fkjava.menu.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.fkjava.identity.domain.Role;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="tb_menu")
public class Menu implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="uuid2",strategy="uuid2")
	@GeneratedValue(generator="uuid2")
	@Column(length=36)
	private String id;
	
	private String name;
	
	private String url;
	
	@Enumerated(EnumType.STRING)
	private Type type;
	
	@ManyToMany
	@JoinTable(name="tb_menu_role",
	joinColumns= {@JoinColumn(name="role_id")})	
	@OrderBy("name")//使用角色名称排序
	private List<Role> roles;
	//用于排序的数字
	private Double number;
	//上级菜单
	@ManyToOne
	@JoinColumn(name="parent_id")//上级菜单的id
	@JsonIgnore//在生成json的时候不要把parent也生成出去
	private Menu parent;
	//下级菜单
	@OneToMany(mappedBy="parent")
	@OrderBy("number")//查询下级菜单的时候使用数字排序
	@JsonProperty("children")//生成json的时候，使用一个别名
	private List<Menu> childs;
	
	public static enum Type{
		/***
		 * 链接类型
		 */
		LINK,
		/***
		 * 按钮类型
		 */
		BUTTON;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Menu> getChilds() {
		return childs;
	}

	public void setChilds(List<Menu> childs) {
		this.childs = childs;
	}
	
	
}

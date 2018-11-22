package org.fkjava.hr.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.fkjava.identity.domain.User;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tb_hr_employee")
public class Employee implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="uuid2",strategy="uuid2")
	@GeneratedValue(generator="uuid2")
	private String id;
	
	/***
	 * 员工关联的用户
	 */
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	/***
	 * 员工所属的部门
	 */
	@ManyToOne
	@JoinTable(name="tb_department_employee",
				joinColumns= {@JoinColumn(name="employee_id")},
				inverseJoinColumns= {@JoinColumn(name="department_id")})
	@JsonIgnore
	private Department department;
	
	/***
	 * 入职时间
	 */
	@Temporal(TemporalType.DATE)
	private Date joinTime;
	
	/***
	 * 状态
	 */
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public static enum Status{
		NORMAL;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}

package org.fkjava.hr.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="tb_hr_department")
public class Department implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="uuid2",strategy="uuid2")
	@GeneratedValue(generator="uuid2")
	@Column(length=36)
	private String id;
	
	private String name;
	/***
	 * 上级部门
	 */
	@ManyToOne
	@JoinColumn(name="parent_id")
	@JsonIgnore
	private Department parent;
	/***
	 * 下级部门
	 */
	@OneToMany(mappedBy="parent")
	@JsonProperty("children")
	@OrderBy("number")
	private List<Department> childs;
	
	private Double number;
	/***
	 * 经理
	 */
	@OneToOne
	@JoinColumn(name="manager_id")
	private Employee manager;
	/***
	 * 员工
	 */
	@OneToMany(mappedBy = "department")
	@JsonIgnore
	private List<Employee> employees;
	
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
	public Department getParent() {
		return parent;
	}
	public void setParent(Department parent) {
		this.parent = parent;
	}
	public List<Department> getChilds() {
		return childs;
	}
	public void setChilds(List<Department> childs) {
		this.childs = childs;
	}
	public Double getNumber() {
		return number;
	}
	public void setNumber(Double number) {
		this.number = number;
	}
	public Employee getManager() {
		return manager;
	}
	public void setManager(Employee manager) {
		this.manager = manager;
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	
}

package org.fkjava.hr.service.impl;

import java.util.List;

import org.fkjava.commons.data.domain.Result;
import org.fkjava.hr.domain.Department;
import org.fkjava.hr.domain.Employee;
import org.fkjava.hr.repository.DepartmentRepository;
import org.fkjava.hr.repository.EmployeeRepository;
import org.fkjava.hr.service.HumanResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class HumanResourcesServiceImpl implements HumanResourcesService {

	@Autowired
	private DepartmentRepository deptRepository; 
	@Autowired
	private EmployeeRepository empRepository;
	
	@Override
	public void saveDepartment(Department dept) {
		if(dept.getParent() != null && StringUtils.isEmpty(dept.getParent().getId())) {
			dept.setParent(null);
		}
		if(StringUtils.isEmpty(dept.getId())) {
			dept.setId(null);
		}
		//1,同级部门不能重名
		Department old;
		if(dept.getParent() != null) {
			old = this.deptRepository.findByParentAndName(dept.getParent(),dept.getName());
		}else {
			old = this.deptRepository.findByNameAndParentNull(dept.getName());
		}
		
		if(old != null && !old.getId().equals(dept.getId())) {
			throw new IllegalArgumentException("部门名称不能重复");
		}
		
		//设置部门经理为当前部门的员工
		if(dept.getManager() != null 
				&& dept.getManager().getUser() != null
				 && !StringUtils.isEmpty(dept.getManager().getUser().getId())) {
			Employee employee = dept.getManager();
			Employee oldEmp = this.empRepository.findByUser(employee.getUser());
			if(oldEmp == null) {
				//没有User对应的员工应该新建一个
				employee = this.empRepository.save(employee);
			}else {
				//原本有员工
				employee = oldEmp;
			}
			employee.setDepartment(dept);
			dept.setManager(employee);
		}else {
			dept.setManager(null);
		}
		
		//进行排序
		if(old != null) {
			dept.setNumber(old.getNumber());
		}else {
			Double maxNumber;
			if(dept.getParent() == null) {
				maxNumber = this.deptRepository.findMaxNumberByParentNull();
			}else {
				maxNumber = this.deptRepository.findMaxNumberByParent(dept.getParent());
			}
			
			if(maxNumber == null) {
				maxNumber = 0.0;
			}
			Double number = maxNumber + 100000000.0;
			dept.setNumber(number);
			
		}
		
		this.deptRepository.save(dept);
	}

	@Override
	public List<Department> findTopDepartment() {
		// TODO Auto-generated method stub
		return this.deptRepository.findByParentNullOrderByNumber();
	}

	@Override
	@Transactional
	public Result deleteDepartment(String id) {
		Department old = this.deptRepository.findById(id).orElse(null);
		if(old != null) {
			if(old.getChilds().isEmpty()) {
				this.deptRepository.delete(old);
				
			}else {
				return Result.error();
			}
		}
		return Result.ok();
	}

}

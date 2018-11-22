package org.fkjava.hr.service;

import java.util.List;

import org.fkjava.commons.data.domain.Result;
import org.fkjava.hr.domain.Department;

public interface HumanResourcesService {

	void saveDepartment(Department dept);

	List<Department> findTopDepartment();

	Result deleteDepartment(String id);

}

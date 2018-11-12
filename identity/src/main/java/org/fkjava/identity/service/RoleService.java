package org.fkjava.identity.service;

import java.util.List;

import org.fkjava.identity.domain.Role;

public interface RoleService {

	//保存
	void save(Role role);

	//查询全部
	List<Role> findAllRoles();

	//根据id删除
	void deleteById(String id);

}

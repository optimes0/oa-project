package org.fkjava.identity.repository;

import java.util.List;
import java.util.Optional;

import org.fkjava.identity.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, String>{

	//根据key进行查找
	Optional<Role> findByRoleKey(String roleKey);

	List<Role> findByFixedTrue();
	
}

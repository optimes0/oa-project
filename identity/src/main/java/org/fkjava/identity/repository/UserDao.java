package org.fkjava.identity.repository;

import org.fkjava.identity.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, String>{

	//根据登录名进行查找
	User findByLoginName(String loginName);

	//根据名字模糊分页查询
	Page<User> findByNameContaining(String keyword, Pageable pageable);

}

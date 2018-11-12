package org.fkjava.identity.service.impl;

import java.util.List;



import org.fkjava.identity.domain.Role;
import org.fkjava.identity.repository.RoleDao;
import org.fkjava.identity.service.RoleService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class RoleServiceImpl implements RoleService,InitializingBean {

	//自动注入roledao
	@Autowired
	private RoleDao roleDao;
	
	@Override
	@Transactional//事务的注解
	public void afterPropertiesSet() throws Exception {
		//在服务启动的时候，需要检查是否有预置的角色，没有则要添加进去
		Role admin = this.roleDao.findByRoleKey("ADMIN")
						.orElse(new Role());//没有则手动new一个role对象进行添加
		admin.setName("超级管理员");
		admin.setRoleKey("ADMIN");
		this.save(admin);
		
		//所有用户的默认角色
		Role user = this.roleDao.findByRoleKey("USER")
						.orElse(new Role());//没有就新增一个对象手动添加
		user.setName("普通用户");
		user.setRoleKey("USER");
		//表明这是一个默认角色
		user.setFixed(true);
		this.save(user);
		
	}
	
	@Override
	public void save(Role role) {
		
		if(StringUtils.isEmpty(role.getId())) {
			//如果id为空则设为null方便新增
			role.setId(null);
		}
		
		Role old = this.roleDao.findByRoleKey(role.getRoleKey()).orElse(null);
		if(		//此时表示新增
				(role.getId() == null && old == null)
				//此时根据key能从数据库里找到role，id相同，表示修改，但key没有修改
				|| (role.getId() != null && old != null && role.getId().equals(old.getId()))
				//此时根据key没能从数据库里找到role，但是id不为null，表示修改key
				|| (role.getId() != null && old == null)
				) {
			this.roleDao.save(role);
		}else {
			//此时要么id为空，数据库不为空
			//或者id为空，数据库为空，但id不相同
			throw new IllegalArgumentException("角色的key是唯一的，不能重复");
		}
		

	}

	@Override
	public List<Role> findAllRoles() {
		// 调用roledao查询所有数据
		return this.roleDao.findAll();
	}

	@Override
	public void deleteById(String id) {
		// 根据id删除数据
		this.roleDao.deleteById(id);
		
	}

	

}

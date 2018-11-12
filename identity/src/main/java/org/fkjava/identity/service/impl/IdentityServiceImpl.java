package org.fkjava.identity.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;



import org.fkjava.identity.domain.Role;
import org.fkjava.identity.domain.User;
import org.fkjava.identity.repository.RoleDao;
import org.fkjava.identity.repository.UserDao;
import org.fkjava.identity.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class IdentityServiceImpl implements IdentityService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private PasswordEncoder passwordEncode;
	
	@Override
	@Transactional
	public void save(User user) {
		//在保存前处理角色
		//1，需要先把数据库里的固定角色查询出来
		List<Role> fixedRole = this.roleDao.findByFixedTrue();
		//2，页面传入的角色
		List<Role> roles = user.getRoles();
		if(roles == null) {
			//如果roles为空 ，手动new一个设进去
			roles = new LinkedList<>();
			user.setRoles(roles);
		}else {
			//根据页面传过来的id查询所有角色
			List<String> ids = new LinkedList<>();
			roles.stream()//转换为流式api
					.map((role)->{
						//role和id的映射关系，把遍历出来的id返回
						return role.getId();
					}).forEach(id->{
						//把遍历出来的id添加进集合里面
						ids.add(id);
					});
			//根据id把数据库里的角色重新查一遍
			List<Role> tmp = this.roleDao.findAllById(ids);
			//重新加进集合里面
			roles.clear();
			roles.addAll(tmp);
		}
		//3，为了确保角色不重复，需要重写Role的equals方法和hashcode方法，把所有角色添加进set里则不会重复
		Set<Role> allRoles = new HashSet<>();
		allRoles.addAll(fixedRole);
		allRoles.addAll(roles);//后面添加的如果有重复不会添加进去
		//把原来的roles清空然后添加set
		roles.clear();
		roles.addAll(allRoles);
		
		
		//1，检查user是否有id，没有就设为null，方便新增
		if(StringUtils.isEmpty(user.getId())) {
			user.setId(null);
		}
		//每次修改后，都使用正常状态
		user.setStatus(User.Status.NORMAL);
		user.setExpiredTime(getExpiredTime());
		
		//2，根据登录名查询用户，用于检查登录名是否被占用
		User old = this.userDao.findByLoginName(user.getLoginName());
		if(old == null) {
			if(!StringUtils.isEmpty(user.getId())) {
				//表示登录名可能被恶意修改，所以数据不变
				old = this.userDao.findById(user.getId()).orElse(null);
				if(!StringUtils.isEmpty(old)) {
					//如果根据id查出来的数据不为空则把数据重新保存回去
					this.userDao.save(old);
				}else {
					//为空则抛异常
					throw new IllegalArgumentException("修改错误");
				}
			}else {
				//没有被占用直接保存
				String password = this.passwordEncode.encode(user.getPassword());
				user.setPassword(password);
				this.userDao.save(user);
			}
			
		}else {
			//还有一种情况是登录名重复，要进行修改
			if(user.getId() != null && user.getId().equals(old.getId())) {
				//有id并且页面传入的id和数据库查出来的id相同，则表示修改
				//此时页面的登录名和数据库的登录名相同，但是因为id相同所以是同一个用户
				if(StringUtils.isEmpty(user.getPassword())) {
					//如果修改的时候没有修改密码，则把原来的密码设进去
					user.setPassword(old.getPassword());
				}else {
					String password = this.passwordEncode.encode(user.getPassword());
					user.setPassword(password);
				}
				this.userDao.save(user);
			}else {
				//此时页面的登录名和数据库的登录名相同，但id不同所以不是同一个用户
				throw new IllegalArgumentException("登录名重复");
			}
		}

	}

	@Override
	public Page<User> findUsers(String keyword, Integer number) {
		//检查是否有关键字，没有则设为空
		if(StringUtils.isEmpty(keyword)) {
			keyword = null;
		}
		
		//分页条件
		Pageable pageable = PageRequest.of(number, 4);
		Page<User> page;
		if(keyword == null) {
			//如果没有关键字，则分页查找所有的数据
			page = this.userDao.findAll(pageable);
		}else {
			//如果有关键字，则根据关键字进行模糊分页查询
			page = this.userDao.findByNameContaining(keyword,pageable);
		}
		return page;
	}

	@Override
	public User findUserById(String id) {
		//orElse:如果没有从数据库找到数据则返回null
		return this.userDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void active(String id) {
		//去数据库根据查找数据
		User user = this.userDao.findById(id).orElse(null);
		if(user != null) {
			user.setExpiredTime(getExpiredTime());
			user.setStatus(User.Status.NORMAL);
		}
	}
	
	private Date getExpiredTime() {
		//加上两个月
		Calendar cal = Calendar.getInstance();
		//获取月份
		int month = cal.get(Calendar.MONTH);
		month += 2;
		cal.set(Calendar.MONTH, month);//把修改的月份设置回去
		//两个月后的时间
		Date time = cal.getTime();
		return time;
	}

	@Override
	@Transactional
	public void disable(String id) {
		User user = this.userDao.findById(id).orElse(null);
		if(user != null) {
			user.setStatus(User.Status.DISABLED);
		}
		
	}

	@Override
	public Optional<User> findByLoginName(String username) {
		User user = this.userDao.findByLoginName(username);
		Optional<User> op = Optional.ofNullable(user);
		return op;
	}

}

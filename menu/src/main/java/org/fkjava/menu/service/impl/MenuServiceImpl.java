package org.fkjava.menu.service.impl;


import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;



import org.fkjava.commons.data.domain.Result;
import org.fkjava.identity.domain.Role;
import org.fkjava.identity.domain.User;
import org.fkjava.identity.repository.RoleDao;
import org.fkjava.identity.repository.UserDao;
import org.fkjava.identity.util.UserHolder;
import org.fkjava.menu.domain.Menu;
import org.fkjava.menu.repository.MenuDao;
import org.fkjava.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;



@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private MenuDao menuDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserDao userDao;
	
	/***
	 * 保存菜单
	 */
	@Override
	public void save(Menu menu) {
		//检查是否有id
		if(StringUtils.isEmpty(menu.getId())) {
			//没有则设为null
			menu.setId(null);
		}
		//判断是否有parent
		if(menu.getParent() != null && StringUtils.isEmpty(menu.getParent().getId())) {
			//有上级菜单，但上级菜单的id为空，则可能被恶意修改,上级菜单为空表示没有上级菜单
			menu.setParent(null);
		}
		//1,检查相同的父菜单下是否有相同的子菜单
		Menu old;
		if(menu.getParent() != null) {
			//如果上级菜单不为空，根据名字和上级菜单进行查询
			old = this.menuDao.findByNameAndParent(menu.getName(),menu.getParent());
		}else {
			//如果上级菜单为空，则直接找parent_id为null的，即为最高级菜单
			old = this.menuDao.findByNameAndParentNull(menu.getName());
		}
		
		if(old != null && !old.getId().equals(menu.getId())) {
			//根据名称从数据库里查到数据，但是id不相同
			throw new IllegalArgumentException("菜单的名字不能重复");
		}
		
		//2,根据选取的角色id，查询角色，解决key重复的问题
		List<String> ids = new LinkedList<>();
		if(menu.getRoles() == null) {
			menu.setRoles(new LinkedList<>());
		}
		//遍历页面传过来的角色的id
		menu.getRoles().forEach(role -> ids.add(role.getId()));
		//根据id查询数据库的所有角色
		List<Role> roles = this.roleDao.findAllById(ids);
		//去重
		Set<Role> set = new HashSet<>();
		set.addAll(roles);
		//先清空原来的role，然后把去重的重新添加进去
		menu.getRoles().clear();
		menu.getRoles().addAll(set);
		//3，设置排序的序号
		//找到同级最大的number并加100000000，形成一个给当前菜单的新数字
		//修改不需要查询
		if(old != null) {
			menu.setNumber(old.getNumber());
		}else {
			Double maxNumber;
			if(menu.getParent() == null) {
				maxNumber = this.menuDao.findMaxNumberByParentNull();
			}else {
				maxNumber = this.menuDao.findMaxNumberByParent(menu.getParent());
			}
			
			if(maxNumber == null) {
				maxNumber = 0.0;
			}
			Double number = maxNumber + 100000000.0;
			menu.setNumber(number);
		}
		
		//4，保存数据
		this.menuDao.save(menu);
	}
	/***
	 * 找到最上级菜单
	 */
	@Override
	public List<Menu> findTopMenus() {
		return this.menuDao.findByParentNullOrderByNumber();
	}
	/***
	 * 对菜单进行排序
	 */
	@Override
	@Transactional
	public Result move(String id, String targetId, String moveType) {
		//根据id先查找数据库
		Menu menu = this.menuDao.findById(id).orElse(null);
		
		if(StringUtils.isEmpty(targetId)) {
			//一定是移动到一级菜单最后面
			Double maxNumber = this.menuDao.findMaxNumberByParentNull();
			if(maxNumber == null) {
				maxNumber = 0.0;
			}
			Double number = maxNumber + 100000000.0;
			menu.setNumber(number);
			menu.setParent(null);
			
			return Result.ok();
		}
		Menu target = this.menuDao.findById(targetId).orElse(null);
		
		if("inner".equals(moveType)) {
			//把menu移动到target里面，此时menu的parent直接改为target就好
			//number则是根据target作为父菜单，找到最大的number然后加上一个数字
			Double maxNumber = this.menuDao.findMaxNumberByParent(target);
			if(maxNumber == null) {
				maxNumber = 0.0;
			}
			Double number = maxNumber + 100000000.0;
			menu.setNumber(number);
			menu.setParent(target);
		}else if("prev".equals(moveType)) {
			//number应该小于target的number并且大于target前一个菜单的number
			Pageable pageable = PageRequest.of(0, 1);//查询第一页，只要一条数据
			Page<Menu> prevs = this.menuDao
						.findByParentAndNumberLessThanOrderByNumberDesc(target.getParent(),target.getNumber(),pageable);
			
			Double next = target.getNumber();
			Double number;
			if(prevs.getNumberOfElements() > 0) {
				Double prev = prevs.getContent().get(0).getNumber();
				number = (prev + next) / 2;
			}else {
				number = next / 2;
			}
			menu.setNumber(number);
			//移动到target之前跟target同级
			menu.setParent(target.getParent());
		}else if("next".equals(moveType)) {
			//number应该大于target的number并且小于target后一个菜单的number
			Pageable pageable = PageRequest.of(0, 1);
			Page<Menu> nexts = this.menuDao
						.findByParentAndNumberGreaterThanOrderByNumberAsc(target.getParent(),target.getNumber(),pageable);
		
			Double prev = target.getNumber();
			Double number;
			if(nexts.getNumberOfElements() > 0) {
				Double next = nexts.getContent().get(0).getNumber();
				number = (prev + next) / 2;
			}else {
				number = prev + 100000000.0;
			}
			menu.setNumber(number);
			//移动到target之后跟target同级
			menu.setParent(target.getParent());
		}else {
			throw new IllegalArgumentException("非法的参数移动类型");
		}
		return Result.ok();
	}
	/***
	 * 删除
	 */
	@Override
	@Transactional
	public Result remove(String id) {
		//先根据id到数据库查找数据
		Menu entity = this.menuDao.findById(id).orElse(null);
		if(entity != null) {
			if(entity.getChilds().isEmpty()) {
				//当查出了的数据不为空，且子菜单为空时才进行删除
				this.menuDao.delete(entity);
			}else {
				return Result.error();
			}
		}
		return Result.ok();
	}
	
	/***
	 * 根据角色权限查找对应的菜单
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Menu> findMyMenus(String userId) {
		//从线程里得到User不是持久化的
//		User user = UserHolder.get();
		//根据id查找持久化的User
		User user = this.userDao.getOne(userId);
		//得到所有的角色
		List<Role> roles = user.getRoles();
		
		//根据角色查找菜单
		List<Menu> menus = this.menuDao.findDistinctByRolesIn(roles);
		//准备返回的一级菜单
		List<Menu> topMenus = new LinkedList<>();
		//循环所有的二级以下菜单并准备一个map集合
		Map<Menu, List<Menu>> map = new HashMap<>();
		menus.stream()
				.filter(menu -> menu.getParent() != null)
				.forEach(menu -> {
					//得到上级
					Menu parent = menu.getParent();
					if(!map.containsKey(parent)) {
						//如果map里面没有parent则设放进去
						map.put(parent, new LinkedList<>());
					}
					//获取子级
					List<Menu> childs = map.get(parent);
					//把子级菜单加入上级菜单里
					childs.add(menu);
				});
		
		//在内存里面对map进行排序
		Comparator<Menu> comparator = (menu1,menu2) -> {
			if(menu1.getNumber() > menu2.getNumber()) {
				return 1;
			}else if(menu1.getNumber() < menu2.getNumber()) {
				return -1;
			}else {
				return 0;
			}
		};
		//构建新的一级菜单，并且把二级菜单加入一级菜单里
		map.entrySet().stream()
					.filter(entry -> entry.getKey().getParent() == null)
					.forEach(entry -> {
						//一级
						Menu parent = entry.getKey();
						//二级
						List<Menu> childs = entry.getValue();
						
						//查询得到的是持久化对象，不要修改，直接new一个用于保存数据
						Menu menu = this.copy(parent);
						//循环二级菜单
						childs.forEach(child -> {
							//把二级菜单加入一级菜单中
							Menu subMenu = this.copy(child);
							menu.getChilds().add(subMenu);
						});
						//排序二级菜单
						menu.getChilds().sort(comparator);
						//把一级菜单加入到集合里
						topMenus.add(menu);
					});
		//给一级菜单排序
		topMenus.sort(comparator);
		return topMenus;
	}

	/***
	 * 把持久化数据转换为瞬态数据
	 * @param persist
	 * @return
	 */
	private Menu copy(Menu persist) {
		Menu menu = new Menu();
		menu.setId(persist.getId());
		menu.setName(persist.getName());
		menu.setNumber(persist.getNumber());
		menu.setUrl(persist.getUrl());
		menu.setChilds(new LinkedList<>());
		return menu;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Set<String> findMyUrls(String userId) {
		User user = this.userDao.getOne(userId);
		List<Role> roles = user.getRoles();
		List<Menu> menus = this.menuDao.findDistinctByRolesIn(roles);
		
		Set<String> urls = new HashSet<>();
		menus.forEach(m -> {
			urls.add(m.getUrl());
		});
		return urls;
	}
	
}

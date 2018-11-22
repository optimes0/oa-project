package org.fkjava.menu.controller;

import java.util.List;

import org.fkjava.commons.data.domain.Result;
import org.fkjava.identity.domain.Role;

import org.fkjava.identity.service.RoleService;
import org.fkjava.menu.domain.Menu;
import org.fkjava.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/menu")
@SessionAttributes("menuJson")
public class MenuController {
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	
	@GetMapping
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("/menu/index");
		List<Role> roles = this.roleService.findAllRoles();
		mav.addObject("roles", roles);
		return mav;
	}
	
	@PostMapping
	public String save(Menu menu) {
		this.menuService.save(menu);
		return "redirect:/menu";
	}
	
	//如果客户端要求返回json则调用下面的方法
	@GetMapping(produces="application/JSON")
	@ResponseBody
	public List<Menu> findTopMenus(){
		return this.menuService.findTopMenus();
	}
	
	@PostMapping(path="move",produces="application/JSON")
	@ResponseBody
	public Result move(String id, String targetId, String moveType) {
		return this.menuService.move(id,targetId,moveType);
	}
	
	@DeleteMapping(path="/{id}",produces="application/JSON")
	@ResponseBody
	public Result remove(@PathVariable("id")String id) {
		return this.menuService.remove(id);
	}
	
	@GetMapping(path="menus", produces="application/JSON")
	@ResponseBody
	public String findMyMenu(@ModelAttribute("menuJson")String menuJson){
		//找到当前用户的菜单
		//TODO 暂时没有用户，先直接查询所有
		return menuJson;
	}
}

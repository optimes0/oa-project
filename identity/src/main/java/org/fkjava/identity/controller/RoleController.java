package org.fkjava.identity.controller;

import java.util.List;

import org.fkjava.identity.domain.Role;
import org.fkjava.identity.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller//表明这是一个控制层
@RequestMapping("/identity/role")//请求映射
public class RoleController {

	//自动注入roleService
	@Autowired
	private RoleService roleService;
	
	//接收get请求方法
	@GetMapping
	public ModelAndView index() {
		
		ModelAndView mav = new ModelAndView("identity/role/index");
		//查找全部数据
		List<Role> roles = this.roleService.findAllRoles();
		//把查出来的数据返回到页面
		mav.addObject("roles", roles);
		return mav;
		
	}
	
	//接收post请求方法
	@PostMapping
	public String index(Role role) {
		//进行保存
		this.roleService.save(role);
		return "redirect:/identity/role";
	}
	
	@DeleteMapping("{id}")
	@ResponseBody
	public String delete(@PathVariable("id")String id) {
		System.out.println("delete()---->");
		this.roleService.deleteById(id);
		return "ok";
	}
}

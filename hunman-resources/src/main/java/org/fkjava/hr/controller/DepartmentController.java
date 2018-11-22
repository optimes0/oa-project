package org.fkjava.hr.controller;

import java.util.List;

import org.fkjava.commons.data.domain.Result;
import org.fkjava.hr.domain.Department;
import org.fkjava.hr.service.HumanResourcesService;
import org.fkjava.identity.domain.User;
import org.fkjava.identity.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/human-resources/department")
public class DepartmentController {

	@Autowired
	private IdentityService identityService;
	@Autowired
	private HumanResourcesService hrService;
	
	@GetMapping
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("human-resources/department/index");
		//暂时模拟
		Page<User> userPage = this.identityService.findUsers(null, 0);
		List<User> users = userPage.getContent();
		mav.addObject("user", users);
		return mav;
	}
	
	@PostMapping
	public String save(Department dept) {
		this.hrService.saveDepartment(dept);
		return "redirect:/human-resources/department";
	}
	
	@GetMapping(produces = "application/json")
	@ResponseBody
	public List<Department> findTopDepartment(){
		return this.hrService.findTopDepartment();
	}
	
	@DeleteMapping(path="{id}",produces="application/json")
	@ResponseBody
	public Result deleteDepartment(@PathVariable("id")String id) {
		
		return this.hrService.deleteDepartment(id);
	}
}

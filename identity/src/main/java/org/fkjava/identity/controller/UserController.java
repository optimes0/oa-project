package org.fkjava.identity.controller;

import java.util.List;

import org.fkjava.identity.domain.Role;
import org.fkjava.identity.domain.User;
import org.fkjava.identity.service.IdentityService;
import org.fkjava.identity.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

@Controller//表明这是个控制器
@RequestMapping("/identity/user")//请求映射
@SessionAttributes({"modifyUserId"})
public class UserController {

	@Autowired
	private IdentityService identityService;
	@Autowired
	private RoleService roleService;
	
	@GetMapping
	public ModelAndView index(@RequestParam(name="pageNumber",defaultValue="0")Integer number,//页码
								@RequestParam(name="keyword",required=false)String keyword//搜索关键字
	) {
		ModelAndView mav = new ModelAndView("identity/user/index");
		Page<User> page = this.identityService.findUsers(keyword,number);
		mav.addObject("page", page);
		return mav;
	}
	
	@GetMapping("/add")
	public ModelAndView add() {
		ModelAndView mav = new ModelAndView("identity/user/add");
		List<Role> roles = this.roleService.findAllRoles();
		mav.addObject("roles", roles);
		return mav;
	}
	
	@PostMapping("/add")
	public String add(User user,
						//拿到session里面的id
						@SessionAttribute(value="modifyUserId",required=false)String modifyUserId,
						SessionStatus sessionStatus) {
		//修改用户的时候需要把id设进user对象里面
		if(modifyUserId != null && 
				//user对象没有id则表示新增，有id则表示修改
				!StringUtils.isEmpty(user.getId())) {
			user.setId(modifyUserId);
		}
		this.identityService.save(user);
		
		//把session里面的id清除掉
		sessionStatus.setComplete();
		
		return "redirect:/identity/user";
	}
	
	@GetMapping("/{id}")
	public ModelAndView detail(@PathVariable("id")String id) {
		//页面添加跟修改一样，只是需要把User根据id查出来
		ModelAndView mav = this.add();
		//查询User
		User user = this.identityService.findUserById(id);
		mav.addObject("user", user);
		//把需要需改的用户id放进session里面，避免浏览器恶意修改
		mav.addObject("modifyUserId", user.getId());
		return mav;
	}
	
	@GetMapping("/active/{id}")
	public String active(@PathVariable("id")String id) {
		this.identityService.active(id);
		return "redirect:/identity/user";
	}
	
	@GetMapping("/disable/{id}")
	public String disable(@PathVariable("id")String id) {
		this.identityService.disable(id);
		return "redirect:/identity/user";
	}
}

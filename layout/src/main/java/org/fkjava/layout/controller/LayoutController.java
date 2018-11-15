package org.fkjava.layout.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/layout")
public class LayoutController {

	@RequestMapping
	public String index() {
		return "layout/index";
	}
	
	@GetMapping("ex")
	public String ex() {
		return "layout/ex";
	}
}

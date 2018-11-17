package org.fkjava.notice.controller;

import java.util.List;

import org.fkjava.notice.domain.NoticeType;
import org.fkjava.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	@GetMapping
	public ModelAndView index(
			@RequestParam(name="pageNumber",defaultValue="0")Integer number,
			@RequestParam(name="keyword",required=false)String keyword
			) {
		ModelAndView mav = new ModelAndView("notice/index");
		return mav;
	}
	
	@GetMapping("/add")
	public ModelAndView add() {
		ModelAndView mav = new ModelAndView("notice/add");
		List<NoticeType> types = this.noticeService.findAll();
		mav.addObject("types",types);
		return mav;
	}
}

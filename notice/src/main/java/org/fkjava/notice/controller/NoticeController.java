package org.fkjava.notice.controller;

import java.util.List;

import org.fkjava.commons.data.domain.Result;
import org.fkjava.notice.domain.Notice;
import org.fkjava.notice.domain.NoticeRead;
import org.fkjava.notice.domain.NoticeType;
import org.fkjava.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
		Page<NoticeRead> page = this.noticeService.findNotices(number,keyword);
		mav.addObject("page", page);
		return mav;
	}
	
	@GetMapping("/add")
	public ModelAndView add() {
		ModelAndView mav = new ModelAndView("notice/add");
		List<NoticeType> types = this.noticeService.findAllTypes();
		mav.addObject("types",types);
		return mav;
	}
	
	@PostMapping
	public String saveNotice(Notice notice) {
		this.noticeService.write(notice);
		return "redirect:/notice";
	}
	
	/***
	 * 发布
	 */
	@PostMapping("publish/{id}")
	@ResponseBody
	public Result publish(@PathVariable("id")String id) {
		this.noticeService.publish(id);
		return Result.ok();
	}
	
	/***
	 * 编辑
	 */
	@GetMapping("edit/{id}")
	public ModelAndView edit(@PathVariable("id")String id) {
		ModelAndView mav = new ModelAndView("notice/add");
		Notice notice = this.noticeService.findNoticeById(id);
		mav.addObject("notice", notice);
		
		List<NoticeType> types = this.noticeService.findAllTypes();
		mav.addObject("types", types);
		return mav;
	}
	
	/***
	 * 删除
	 */
	@DeleteMapping("{id}")
	@ResponseBody
	public Result delete(@PathVariable("id")String id) {
		this.noticeService.deleteNotice(id);
		return Result.ok();
	}
	
	/***
	 * 撤回
	 */
	@PostMapping("recall/{id}")
	@ResponseBody
	public Result recall(@PathVariable("id")String id) {
		this.noticeService.recallNotice(id);
		return Result.ok();
	}
	
	/***
	 * 阅读
	 */
	@GetMapping("{id}")
	public ModelAndView read(@PathVariable("id")String id) {
		ModelAndView mav = new ModelAndView("notice/read");
		Notice notice = this.noticeService.findNoticeById(id);
		NoticeRead nr = this.noticeService.findNoticeReadByNotice(id);
		mav.addObject("notice", notice);
		mav.addObject("noticeRead", nr);
		return mav;
	}
	/***
	 * 已阅读
	 */
	@PostMapping("readed/{id}")
	@ResponseBody
	public Result readed(@PathVariable("id")String id) {
		this.noticeService.readed(id);
		return Result.ok();
	}
}

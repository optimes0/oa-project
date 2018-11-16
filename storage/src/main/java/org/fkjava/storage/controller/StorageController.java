package org.fkjava.storage.controller;

import java.io.IOException;
import java.io.InputStream;

import org.fkjava.commons.data.domain.Result;
import org.fkjava.storage.domain.FileInfo;
import org.fkjava.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
@RequestMapping("/storage/file")
public class StorageController {

	@Autowired
	private StorageService storageService;
	
	//列表页面
	@RequestMapping
	public ModelAndView index(
			@RequestParam(name="pageNumber",defaultValue="0")Integer number,
			@RequestParam(name="keyword",required=false)String keyword
			) {
		ModelAndView mav = new ModelAndView("storage/index");
		Page<FileInfo> page = this.storageService.findFile(keyword,number);
		mav.addObject("page", page);
		return mav;
	}
	
	//上传
	@PostMapping
	public String upload(@RequestParam("file")MultipartFile file) throws IOException {
		FileInfo info = new FileInfo();
		info.setName(file.getOriginalFilename());
		info.setFileSize(file.getSize());
		info.setContentType(file.getContentType());
		
		try(InputStream in = file.getInputStream()){
			this.storageService.save(in,info);
		}
		return "redirect:/storage/file";
	}
	
	//下载
	@GetMapping("{id}")
	public ResponseEntity<StreamingResponseBody> download(@PathVariable("id")String id){
		return null;
	}
	
	//删除
	@DeleteMapping("{id}")
	@ResponseBody
	public Result delete(@PathVariable("id")String id) {
		return null;
	}
}

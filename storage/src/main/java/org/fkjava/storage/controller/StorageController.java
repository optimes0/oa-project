package org.fkjava.storage.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.fkjava.commons.data.domain.Result;
import org.fkjava.storage.domain.FileInfo;
import org.fkjava.storage.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
@RequestMapping("/storage/file")
public class StorageController {

	//日志记录
	private static final Logger Log = LoggerFactory.getLogger(StorageController.class); 
	
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
	public ResponseEntity<StreamingResponseBody> download(
			//文件的id
			@PathVariable("id")String id,
			//请求头
			@RequestHeader("User-Agent")String userAgent
			){
		Log.trace("userArgent:" + userAgent);
		
		FileInfo info = this.storageService.findById(id);
		if(info == null) {
			Log.trace("文件信息没有找到");
			return send404();
		}
		//读取文件内容
		InputStream in = this.storageService.getFileContent(info);
		if(in == null) {
			Log.trace("文件内容没有找到");
			return send404();
		}
		
		//构建响应体
		BodyBuilder builder = ResponseEntity.ok();
		//文件大小
		builder.contentLength(info.getFileSize());
		//文件类型
		builder.contentType(MediaType.valueOf(info.getContentType()));
		String name = info.getName();
		//对文件名进行编码，避免出现乱码
		name = URLEncoder.encode(name, Charset.forName("UTF-8"));
		builder.header("Content-Disposition", "attachment;filename*=UTF-8''" + name);
		
		StreamingResponseBody body = new StreamingResponseBody() {
			
			@Override
			public void writeTo(OutputStream outputStream) throws IOException {
				try(in){
					byte[] b = new byte[2048];
					for(int len = in.read(b); len!=-1; len = in.read(b)) {
						outputStream.write(b, 0, len);
					}
				}
				
			}
		};
		
		ResponseEntity<StreamingResponseBody> entity = builder.body(body);
		return entity;
	}
	
	private ResponseEntity<StreamingResponseBody> send404(){
		BodyBuilder builder = ResponseEntity.status(HttpStatus.NOT_FOUND);
		builder.contentType(MediaType.valueOf("text/html;charset=UTF-8"));
		StreamingResponseBody body = (out) -> {
			out.write("文件没有找到".getBytes(Charset.forName("UTF-8")));
		};
		ResponseEntity<StreamingResponseBody> entity = builder.body(body);
		return entity;
	}
	
	//删除
	@DeleteMapping("/{id}")
	@ResponseBody
	public Result delete(@PathVariable("id")String id) {
		
		return this.storageService.deleteFile(id);
	}
}

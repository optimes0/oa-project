package org.fkjava.storage.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;

import org.fkjava.identity.domain.User;
import org.fkjava.identity.util.UserHolder;
import org.fkjava.storage.domain.FileInfo;
import org.fkjava.storage.repository.StorageRepository;
import org.fkjava.storage.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class StorageServiceImpl implements StorageService{
	
	//日志
	private static final Logger log = LoggerFactory.getLogger(StorageServiceImpl.class);
	
	@Value(value="${fkjava.storage.dir?:/tmp/storage}")
	private String dir;
	
	@Autowired
	private StorageRepository storageRepository;
	
	@Override
	public void save(InputStream in, FileInfo info) {
		//保存文件
		String path = UUID.randomUUID().toString();
		File file = new File(dir,path);
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		log.trace("文件的实际存储路径：" + file.getAbsolutePath());
		Path target = file.toPath();
		try {
			Files.copy(in, target);
		}catch (IOException e) {
			throw new RuntimeException("文件存储出错"+e.getLocalizedMessage(),e);
		}
		
		//保存文件信息
		info.setOwner(UserHolder.get());
		info.setUploadTime(new Date());
		info.setPath(path);
		
		this.storageRepository.save(info);
	}

	@Override
	public Page<FileInfo> findFile(String keyword, Integer number) {
		User user = UserHolder.get();
		if(StringUtils.isEmpty(keyword)) {
			keyword = null;
		}
		Pageable pageable = PageRequest.of(number, 5);
		Page<FileInfo> page;
		if(keyword == null) {
			page = this.storageRepository.findByOwner(user,pageable);
		}else {
			page = this.storageRepository.findByOwnerAndNameContaining(user,keyword,pageable);
		}
		
		return page;
	}

}

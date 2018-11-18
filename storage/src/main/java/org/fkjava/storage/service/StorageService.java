package org.fkjava.storage.service;

import java.io.InputStream;

import org.fkjava.commons.data.domain.Result;
import org.fkjava.storage.domain.FileInfo;
import org.springframework.data.domain.Page;

public interface StorageService {

	void save(InputStream in, FileInfo info);

	Page<FileInfo> findFile(String keyword, Integer number);

	FileInfo findById(String id);

	InputStream getFileContent(FileInfo info);

	Result deleteFile(String id);

}

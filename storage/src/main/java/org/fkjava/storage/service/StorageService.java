package org.fkjava.storage.service;

import java.io.InputStream;

import org.fkjava.storage.domain.FileInfo;
import org.springframework.data.domain.Page;

public interface StorageService {

	void save(InputStream in, FileInfo info);

	Page<FileInfo> findFile(String keyword, Integer number);

}

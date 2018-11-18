package org.fkjava.notice.service;

import java.util.List;

import org.fkjava.notice.domain.Notice;
import org.fkjava.notice.domain.NoticeType;

public interface NoticeService {

	void save(NoticeType type);

	List<NoticeType> findAll();

	void delete(String id);

	void write(Notice notice);


}

package org.fkjava.notice.service.impl;

import java.util.List;

import org.fkjava.notice.domain.NoticeType;
import org.fkjava.notice.repository.NoticeTypeRepository;
import org.fkjava.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeTypeRepository typeRepository;
	
	@Override
	public void save(NoticeType type) {
		if(StringUtils.isEmpty(type.getId())) {
			type.setId(null);
		}

		NoticeType old = this.typeRepository.findByName(type.getName()).orElse(null);
		if(old == null || old.getId().equals(type.getId())) {
			//要么新增要么修改
			this.typeRepository.save(type);
		}else {
			throw new IllegalArgumentException("公告类型的名称不能重复");
		}
	}

	@Override
	public List<NoticeType> findAll() {
		return this.typeRepository.findAll();
	}

	@Override
	public void delete(String id) {
		this.typeRepository.deleteById(id);
		
	}

}

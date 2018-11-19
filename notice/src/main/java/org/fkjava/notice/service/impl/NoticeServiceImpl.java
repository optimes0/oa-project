package org.fkjava.notice.service.impl;


import java.util.Date;
import java.util.List;

import org.fkjava.identity.domain.User;
import org.fkjava.identity.util.UserHolder;
import org.fkjava.notice.domain.Notice;
import org.fkjava.notice.domain.Notice.Status;
import org.fkjava.notice.domain.NoticeRead;
import org.fkjava.notice.domain.NoticeType;
import org.fkjava.notice.repository.NoticeReadRepository;
import org.fkjava.notice.repository.NoticeRepository;
import org.fkjava.notice.repository.NoticeTypeRepository;
import org.fkjava.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;



@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeTypeRepository typeRepository;
	@Autowired
	private NoticeRepository noticeRepository;
	@Autowired
	private NoticeReadRepository readRepository;
	
	/***
	 * 保存公告类型
	 * @param type
	 */
	@Override
	public void saveType(NoticeType type) {
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

	/***
	 * 找到所有的公告类型
	 * @return
	 */
	@Override
	public List<NoticeType> findAllTypes() {
		return this.typeRepository.findAll();
	}

	/***
	 * 删除类型
	 * @param id
	 */
	@Override
	public void deleteType(String id) {
		this.typeRepository.deleteById(id);
		
	}

	/***
	 * 保存公告
	 * @param notice
	 */
	@Override
	public void write(Notice notice) {
		//填充字段
		notice.setAuthor(UserHolder.get());
		notice.setReleaseTime(null);
		notice.setWriteTime(new Date());
		notice.setStatus(Status.DRAFT);
		
		if(StringUtils.isEmpty(notice.getId())) {
			notice.setId(null);
		}
		//保存公告
		this.noticeRepository.save(notice);
		
	}

	/***
	 * 分页查询所有公告
	 * @param number
	 * @param keyword
	 * @return
	 */
	@Override
	public Page<NoticeRead> findNotices(Integer number, String keyword) {
		
		User author = UserHolder.get();
		Pageable pageable = PageRequest.of(number, 5);
		Page<NoticeRead> page = this.readRepository.findNotices(author,author,pageable);
		return page;
	}

	/***
	 * 发布公告
	 * @param id
	 */
	@Override
	@Transactional
	public void publish(String id) {
		//根据id进行查询
		Notice n = this.findNoticeById(id);
		if(n != null) {
			n.setStatus(Status.RELEASED);
			n.setReleaseTime(new Date());
		}
	}

	/***
	 * 根据id查询公告
	 * @param id
	 * @return
	 */
	@Override
	public Notice findNoticeById(String id) {
		return this.noticeRepository.findById(id).orElse(null);
	}

	/***
	 * 删除公告
	 * @param id
	 */
	@Override
	public void deleteNotice(String id) {
		Notice n = this.findNoticeById(id);
		if(n != null) {
			this.noticeRepository.delete(n);
		}
		
	}

	/***
	 * 撤回公告
	 * @param id
	 */
	@Override
	@Transactional
	public void recallNotice(String id) {
		Notice n = this.findNoticeById(id);
		if(n != null) {
			n.setStatus(Status.RECALL);
		}
		
	}

	/***
	 * 已阅读
	 */
	@Override
	public void readed(String id) {
		User user = UserHolder.get();
		Notice n = this.findNoticeById(id);
		Date readTime = new Date();
		
		NoticeRead old = this.readRepository.findByNoticeAndReader(n,user).orElse(null);
		if(old == null) {
			NoticeRead nr = new NoticeRead();
			nr.setNotice(n);
			nr.setReader(user);
			nr.setReadTime(readTime);
			
			this.readRepository.save(nr);
		}
		
	}

	/***
	 * 根据Notice的id以及用户查找NoticeRead
	 * @param id
	 * @return
	 */
	@Override
	public NoticeRead findNoticeReadByNotice(String id) {
		User user = UserHolder.get();
		Notice n = this.findNoticeById(id);
		if(n != null) {
			return this.readRepository.findByNoticeAndReader(n, user).orElse(null);
		}else {
			return null;
		}
		
	}

	

}

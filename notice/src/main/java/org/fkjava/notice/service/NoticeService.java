package org.fkjava.notice.service;

import java.util.List;

import org.fkjava.notice.domain.Notice;
import org.fkjava.notice.domain.NoticeRead;
import org.fkjava.notice.domain.NoticeType;
import org.springframework.data.domain.Page;

public interface NoticeService {
	/***
	 * 保存公告类型
	 * @param type
	 */
	void saveType(NoticeType type);
	/***
	 * 找到所有的公告类型
	 * @return
	 */
	List<NoticeType> findAllTypes();
	/***
	 * 删除类型
	 * @param id
	 */
	void deleteType(String id);
	/***
	 * 保存公告
	 * @param notice
	 */
	void write(Notice notice);
	/***
	 * 分页查询所有公告
	 * @param number
	 * @param keyword
	 * @return
	 */
	Page<NoticeRead> findNotices(Integer number, String keyword);
	/***
	 * 发布公告
	 * @param id
	 */
	void publish(String id);
	/***
	 * 根据id查询公告
	 * @param id
	 * @return
	 */
	Notice findNoticeById(String id);
	/***
	 * 删除公告
	 * @param id
	 */
	void deleteNotice(String id);
	/***
	 * 撤回公告
	 * @param id
	 */
	void recallNotice(String id);
	/***
	 * 已阅读
	 * @param id
	 */
	void readed(String id);
	/***
	 * 根据Notice的id查找NoticeRead
	 * @param id
	 * @return
	 */
	NoticeRead findNoticeReadByNotice(String id);


}

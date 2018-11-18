package org.fkjava.notice.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.fkjava.identity.domain.User;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="tb_notice_read")
public class NoticeRead implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/***
	 * id
	 */
	@Id
	@GenericGenerator(name="uuid2",strategy="uuid2")
	@GeneratedValue(generator="uuid2")
	@Column(length=36)
	private String id;
	/***
	 * 谁阅读了
	 */
	@ManyToOne
	@JoinColumn(name="reader_user_id")
	private User reader;
	/***
	 * 阅读时间
	 */
	private Date readTime;
	/***
	 * 阅读了哪个公告
	 */
	@ManyToOne
	@JoinColumn(name="notice_id")
	private Notice notice;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getReader() {
		return reader;
	}
	public void setReader(User reader) {
		this.reader = reader;
	}
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	public Notice getNotice() {
		return notice;
	}
	public void setNotice(Notice notice) {
		this.notice = notice;
	}
	
	
}

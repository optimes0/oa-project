package org.fkjava.notice.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.fkjava.identity.domain.User;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="tb_notice")
public class Notice implements Serializable{

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
	 * 标题
	 */
	private String title;
	/***
	 * 公告类型
	 */
	@ManyToOne
	@JoinColumn(name="type_id")
	private NoticeType type;
	/***
	 * 作者
	 */
	@ManyToOne
	@JoinColumn(name="authoe_user_id")
	private User author;
	/***
	 * 编写时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date writeTime;
	/***
	 * 发布时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date releaseTime;
	/***
	 * 状态
	 */
	@Enumerated(EnumType.STRING)
	private Status status;
	/***
	 * 公告内容
	 */
	@Lob// 大的数据、长的字段，这种字段不适合LIKE查询，如果要模糊查询就需要结合后面的Lucene、Solr技术
	private String content;
	/***
	 * 记录用户的阅读状态
	 */
	@OneToMany(mappedBy="notice")
	private List<NoticeRead> reads;
	
	public static enum Status{
		/***
		 * 草稿
		 */
		DRAFT,
		/***
		 * 已发布
		 */
		RELEASED,
		/***
		 * 已撤回
		 */
		RECALL;
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public NoticeType getType() {
		return type;
	}

	public void setType(NoticeType type) {
		this.type = type;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<NoticeRead> getReads() {
		return reads;
	}

	public void setReads(List<NoticeRead> reads) {
		this.reads = reads;
	}
	
	
	
}

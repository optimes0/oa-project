package org.fkjava.notice.repository;

import java.util.Optional;

import org.fkjava.identity.domain.User;
import org.fkjava.notice.domain.Notice;
import org.fkjava.notice.domain.NoticeRead;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeReadRepository extends JpaRepository<NoticeRead, String>{

	@Query("select new NoticeRead(nr.id,nr.readTime,n) "
			+ "from Notice n "
			+ "left outer join NoticeRead nr on nr.notice = n and nr.reader = :user "
			+ "where (n.author = :author and (n.status = 'DRAFT' or n.status = 'RECALL') "
			+ "or n.status = 'RELEASED') "
			+ "order by case n.status when 'DRAFT' then 0 when 'RECALL' then 20 when 'RELEASED' then 1 end asc, "
			+ "n.releaseTime desc")
	Page<NoticeRead> findNotices(@Param("author")User author,@Param("user")User user, Pageable pageable);

	Optional<NoticeRead> findByNoticeAndReader(Notice n, User user);


}

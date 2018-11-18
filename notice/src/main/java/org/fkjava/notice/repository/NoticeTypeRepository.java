package org.fkjava.notice.repository;

import java.util.Optional;

import org.fkjava.notice.domain.NoticeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeTypeRepository extends JpaRepository<NoticeType, String>{

	Optional<NoticeType> findByName(String name);

}

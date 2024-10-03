package com.cosimba.dive.domain.josa.repository;

import com.cosimba.dive.domain.josa.entity.Josa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// JosaRepository 는 JPA 를 이용한 데이터베이스 접근을 담당
// 조사 일련번호(serialNumber)나 id로 데이터를 찾을 수 있다!
public interface JosaRepository extends JpaRepository<Josa, Long> {
    // SELECT * FROM josa WHERE id = ? AND is_deleted = false;
    Optional<Josa> findByIdAndIsDeletedIsFalse(Long Id);

    // SELECT * FROM josa WHERE is_deleted = false;
    List<Josa> findAllByIsDeletedIsFalse();
}

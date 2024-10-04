package com.cosimba.dive.domain.clean.repository;

import com.cosimba.dive.domain.clean.entity.Clean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CleanRepository extends JpaRepository<Clean, Long> {
    Optional<Clean> findByJosaIdAndIsDeletedIsFalse(Long josaId);
    Optional<Clean> findByIdAndIsDeletedIsFalse(Long Id);


}

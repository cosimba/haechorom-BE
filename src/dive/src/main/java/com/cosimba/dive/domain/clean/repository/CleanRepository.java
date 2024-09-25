package com.cosimba.dive.domain.clean.repository;

import com.cosimba.dive.domain.clean.entity.Clean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CleanRepository extends JpaRepository<Clean, Long> {
}

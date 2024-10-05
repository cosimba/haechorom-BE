package com.cosimba.dive_batch.repository;

import com.cosimba.dive_batch.domain.AggregatedResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregateResultRepository extends JpaRepository<AggregatedResult, Long> {
}

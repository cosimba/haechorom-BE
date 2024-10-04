package com.cosimba.dive_batch.repository;

import com.cosimba.dive_batch.job.AggregatedResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregateResultRepository extends JpaRepository<AggregatedResult, Long> {
}

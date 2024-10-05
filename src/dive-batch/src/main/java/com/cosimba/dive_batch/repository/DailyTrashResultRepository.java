package com.cosimba.dive_batch.repository;

import com.cosimba.dive_batch.domain.DailyTrashResult;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public interface DailyTrashResultRepository extends ElasticsearchRepository<DailyTrashResult, Long>{
    List<DailyTrashResult> findByCreatedAtBetween(String start, String end);

}

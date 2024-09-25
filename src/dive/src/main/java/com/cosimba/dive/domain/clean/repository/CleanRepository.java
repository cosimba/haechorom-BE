package com.cosimba.dive.domain.clean.repository;

import com.cosimba.dive.domain.clean.entity.Clean;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CleanRepository extends ElasticsearchRepository<Clean, String> {
}

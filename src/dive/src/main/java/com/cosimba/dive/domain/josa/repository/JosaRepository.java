package com.cosimba.dive.domain.josa.repository;

import com.cosimba.dive.domain.josa.entity.Josa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface JosaRepository extends ElasticsearchRepository<Josa, String>{
}

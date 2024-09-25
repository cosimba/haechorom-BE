package com.cosimba.dive_batch.test;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.cosimba.dive.domain.josa.entity.Josa;
import com.cosimba.dive_batch.job.AggregatedResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class ESTest {
    private final ElasticsearchClient client;

    public ESTest(ElasticsearchClient client) {
        this.client = client;
    }

    public AggregatedResult read() throws Exception {
        // 집계 쿼리 준비
        SearchResponse<Josa> searchResponse = client.search(s -> s
                .index("josa_trash") // 인덱스 이름 설정
                .size(0) // 검색 결과는 필요 없으므로 size 0
                .aggregations("collection_by_date", a -> a
                        .dateHistogram(dh -> dh
                                .field("josa_date")
                                .calendarInterval(CalendarInterval.Day) // 일 단위로 그룹화
                                .format("yyyy-MM-dd")
                        )
                        .aggregations("by_beach", agg -> agg
                                .terms(t -> t
                                        .field("coast_name.keyword") // 해안명으로 그룹화
                                )
                                .aggregations("average_collected", agg2 -> agg2
                                        .avg(b -> b.field("predict_val")) // 수거량 환산 필드의 평균
                                )
                        )
                ), Josa.class
        );

        Map<String, Aggregate> aggregations = searchResponse.aggregations();
        DateHistogramAggregate dateHistogram = aggregations.get("collection_by_date").dateHistogram();
        for(DateHistogramBucket dateBucket : dateHistogram.buckets().array()){
            String date = dateBucket.keyAsString();

            Aggregate byBeachAgg = dateBucket.aggregations().get("by_beach");
            if(byBeachAgg.isSterms()){
                StringTermsAggregate byBeach = byBeachAgg.sterms();

                for(StringTermsBucket beachBucket : byBeach.buckets().array()){
                    String beachName = beachBucket.key().stringValue();

                    Aggregate avgCollectedAgg = beachBucket.aggregations().get("average_collected");
                    if(avgCollectedAgg.isAvg()){
                        AvgAggregate avgCollected = avgCollectedAgg.avg();
                        Double averageCollected = avgCollected.value();

                        log.info("date: " + date + ", beach: " + beachName + ", average collected: " + averageCollected);
                    }
                }
            }

        }

        return null;
    }
}

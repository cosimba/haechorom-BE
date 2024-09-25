package com.cosimba.dive_batch.job;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.cosimba.dive.domain.josa.entity.Josa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ElasticsearchAggregationReaderTest {

//    private ElasticsearchClient client;
//    private ElasticsearchAggregationReader reader;
//
//    @BeforeEach
//    void setUp() {
//        client = mock(ElasticsearchClient.class);
//        reader = new ElasticsearchAggregationReader(client);
//    }
//
//    @Test
//    void testRead() throws Exception {
//        // Mocking the average collected value
//        AvgAggregate avgAggregate = AvgAggregate.of(a -> a.value(100.0));
//
//        // Mocking the terms bucket for beaches
//        StringTermsBucket beachBucket = StringTermsBucket.of(b -> b
//                .key(k -> k.stringValue("Beach A"))
//                .aggregations(Map.of("average_collected", Aggregate.of(a -> a.avg(avgAggregate))))
//        );
//
//        // Mocking the terms aggregation for "by_beach"
//        StringTermsAggregate byBeachAgg = StringTermsAggregate.of(t -> t
//                .buckets(b -> b.array(Collections.singletonList(beachBucket)))
//        );
//
//        // Mocking the date histogram bucket
//        DateHistogramBucket dateBucket = DateHistogramBucket.of(db -> db
//                .keyAsString("2023-09-23")
//                .aggregations(Map.of("by_beach", Aggregate.of(a -> a.sterms(byBeachAgg))))
//        );
//
//        // Mocking the date histogram aggregation
//        DateHistogramAggregate dateHistogram = DateHistogramAggregate.of(dh -> dh
//                .buckets(b -> b.array(Collections.singletonList(dateBucket)))
//        );
//
//        // Mocking the aggregations map
//        Map<String, Aggregate> aggregationsMap = Map.of("collection_by_date", Aggregate.of(a -> a.dateHistogram(dateHistogram)));
//
//        // Mocking the SearchResponse
//        SearchResponse<Josa> mockResponse = SearchResponse.of(sr -> sr.aggregations(aggregationsMap));
//
//        // When client.search() is called, return the mock response
//        when(client.search((SearchRequest) any(), Mockito.eq(Josa.class))).thenReturn(mockResponse);
//
//        // Execute the read method
//        AggregatedResult result = reader.read();
//
//        // Validate that result is null since the method currently returns null
//        assertNull(result);
//    }
    private final ElasticsearchClient client;

    public ElasticsearchAggregationReader(ElasticsearchClient client) {
        this.client = client;
    }

    @Test
    void test() {


        SearchResponse<Josa> searchResponse = client.search(s -> s
                .index("josa_trash") // 인덱스 이름 설정
                .size(0) // 검색 결과는 필요 없으므로 size 0
                .aggregations("collection_by_date", a -> a
                        .dateHistogram(dh -> dh
                                .field("josaDate")
                                .calendarInterval(CalendarInterval.Day) // 일 단위로 그룹화
                                .format("yyyy-MM-dd")
                        )
                        .aggregations("by_beach", agg -> agg
                                .terms(t -> t
                                        .field("coastName.keyword") // 해안명으로 그룹화
                                )
                                .aggregations("average_collected", agg2 -> agg2
                                        .avg(b -> b.field("predictVal")) // 수거량 환산 필드의 평균
                                )
                        )
                ), Josa.class
        );
    }
}

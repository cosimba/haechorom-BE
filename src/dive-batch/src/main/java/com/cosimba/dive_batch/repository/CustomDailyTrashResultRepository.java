package com.cosimba.dive_batch.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.*;

import co.elastic.clients.elasticsearch.core.SearchResponse;

import co.elastic.clients.json.JsonData;
import com.cosimba.dive_batch.dto.AggregateResultDto;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CustomDailyTrashResultRepository {

    private final ElasticsearchClient client;

    public List<AggregateResultDto> getAvgByCoastalName() throws Exception {

        // 집계 쿼리 준비
        SearchResponse<AggregateResultDto> searchResponse = client.search(s -> s
                .index("daily_trash_result") // 인덱스 이름 설정
                .size(0) // 검색 결과는 필요 없으므로 size 0
                .query(q -> q
                        .range(r -> r
                                .field("createdAt")
                                .lte(JsonData.of(
                                        LocalDateTime.now()
                                                .atZone(ZoneId.systemDefault())
                                                .toInstant()
                                                .toEpochMilli())) // 쿼리에서 종료일 설정
                        )
                )
                .aggregations("coastal_names", a -> a
                        .terms(t -> t
                                .field("coastalName") // 해안명으로 그룹화
                                .size(1000)
                        )
                        .aggregations("average_total_collected", avg -> avg
                                .avg(b -> b.field("totalCollected")) // 수거량 필드의 평균
                        )
                        .aggregations("average_val", avg -> avg
                                .avg(c -> c.field("averageVal")) // 평균 값 필드의 평균
                        )
                ), AggregateResultDto.class
        );

        // 결과 변환 로직
        List<AggregateResultDto> results = new ArrayList<>();

        // 해안명(terms) 집계를 가져옴
        Map<String, Aggregate> aggregations = searchResponse.aggregations();
        Aggregate coastalNamesAgg = aggregations.get("coastal_names");

        if (coastalNamesAgg != null && coastalNamesAgg.isSterms()) {
            List<StringTermsBucket> buckets = coastalNamesAgg.sterms().buckets().array();

            for (StringTermsBucket bucket : buckets) {
                String coastalName = bucket.key().stringValue(); // 해안명 추출

                // 하위 집계에서 평균값 추출
                double averageTotalCollected = 0.0;
                double averageVal = 0.0;

                Aggregate avgTotalCollectedAgg = bucket.aggregations().get("average_total_collected");
                Aggregate avgValAgg = bucket.aggregations().get("average_val");

                if (avgTotalCollectedAgg != null && avgTotalCollectedAgg.isAvg()) {
                    averageTotalCollected = avgTotalCollectedAgg.avg().value();
                }

                if (avgValAgg != null && avgValAgg.isAvg()) {
                    averageVal = avgValAgg.avg().value();
                }

                // AggregateResultDto에 추가
                AggregateResultDto dto = AggregateResultDto.create(coastalName, averageTotalCollected, averageVal);
                results.add(dto);
            }
        }

        return results; // 결과 반환
    }

    public List<AggregateResultDto> getAvgByCoastalNameByDate(LocalDateTime start, LocalDateTime end) throws Exception {


        // 집계 쿼리 준비
        SearchResponse<AggregateResultDto> searchResponse = client.search(s -> s
                .index("daily_trash_result") // 인덱스 이름 설정
                .size(0) // 검색 결과는 필요 없으므로 size 0
                .query(q -> q
                        .range(r -> r
                                .field("createdAt")
                                .gte(JsonData.of(
                                        start
                                                .atZone(ZoneId.systemDefault())
                                                .toInstant()
                                                .toEpochMilli())) // 쿼리에서 시작일 설정
                                .lte(JsonData.of(
                                        end
                                                .atZone(ZoneId.systemDefault())
                                                .toInstant()
                                                .toEpochMilli())) // 쿼리에서 종료일 설정
                        )
                )
                .aggregations("coastal_names", a -> a
                        .terms(t -> t
                                .field("coastalName") // 해안명으로 그룹화
                                .size(1000)
                        )
                        .aggregations("average_total_collected", avg -> avg
                                .avg(b -> b.field("totalCollected")) // 수거량 필드의 평균
                        )
                        .aggregations("average_val", avg -> avg
                                .avg(c -> c.field("averageVal")) // 평균 값 필드의 평균
                        )
                ), AggregateResultDto.class
        );

        // 결과 변환 로직
        List<AggregateResultDto> results = new ArrayList<>();

        // 해안명(terms) 집계를 가져옴
        Map<String, Aggregate> aggregations = searchResponse.aggregations();
        Aggregate coastalNamesAgg = aggregations.get("coastal_names");

        if (coastalNamesAgg != null && coastalNamesAgg.isSterms()) {
            List<StringTermsBucket> buckets = coastalNamesAgg.sterms().buckets().array();

            for (StringTermsBucket bucket : buckets) {
                String coastalName = bucket.key().stringValue(); // 해안명 추출

                // 하위 집계에서 평균값 추출
                double averageTotalCollected = 0.0;
                double averageVal = 0.0;

                Aggregate avgTotalCollectedAgg = bucket.aggregations().get("average_total_collected");
                Aggregate avgValAgg = bucket.aggregations().get("average_val");

                if (avgTotalCollectedAgg != null && avgTotalCollectedAgg.isAvg()) {
                    averageTotalCollected = avgTotalCollectedAgg.avg().value();
                }

                if (avgValAgg != null && avgValAgg.isAvg()) {
                    averageVal = avgValAgg.avg().value();
                }

                // AggregateResultDto에 추가
                AggregateResultDto dto = AggregateResultDto.create(coastalName, averageTotalCollected, averageVal);
                results.add(dto);
            }
        }

        return results; // 결과 반환
    }

}

package com.cosimba.dive_batch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Document(indexName = "daily_trash_result")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Mapping(mappingPath = "elasticsearch/mappings/Daily-Trash-Result-mapping.json")
@Getter
public class DailyTrashResult {
    @Id
    private Long id;
    private String coastalName;
    private double totalCollected;
    private double averageVal;
    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime createdAt;

    public static DailyTrashResult create(String coastName, double totalValue, double averageValue) {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC); // UTC 기준으로 현재 시간 생성
        return DailyTrashResult.builder()
                .coastalName(coastName)
                .totalCollected(totalValue)
                .averageVal(averageValue)
                .createdAt(now)
                .build();
    }
}

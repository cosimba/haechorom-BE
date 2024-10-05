package com.cosimba.dive_batch.dto.request;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class AggregateDate {
    private Integer year;
    private Integer month;
    private Integer day;

    public static AggregateDate create(Integer year, Integer month, Integer day) {
        return AggregateDate.builder()
                .year(year)
                .month(month)
                .day(day)
                .build();
    }

}

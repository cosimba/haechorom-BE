package com.cosimba.dive_batch.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AggregateResultDto {
    private String coastalName;
    private double totalCollected;
    private double averageVal;

    public static AggregateResultDto create(String coastName, double totalValue, double averageValue) {
        return AggregateResultDto.builder()
                .coastalName(coastName)
                .totalCollected(totalValue)
                .averageVal(averageValue)
                .build();
    }
}

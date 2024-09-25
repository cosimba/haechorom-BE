package com.cosimba.dive_batch.job;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class AggregatedResult {
    private String coastalName;
    private double totalCollected;
    private double averageLength;

    // Constructors, getters, setters
}


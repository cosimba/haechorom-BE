package com.cosimba.dive_batch.domain;

import com.cosimba.dive.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
public class AggregatedResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "coastal_name")
    private String coastalName;
    @Column(name = "total_collected")
    private double totalCollected;
    @Column(name = "average_val")
    private double averageVal;

    public static AggregatedResult create(String coastName, double totalValue, double averageValue) {
        return AggregatedResult.builder()
                .coastalName(coastName)
                .totalCollected(totalValue)
                .averageVal(averageValue)
                .build();
    }
}


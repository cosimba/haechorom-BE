package com.cosimba.dive.domain.clean.payload.dto.response;

import com.cosimba.dive.domain.clean.entity.Clean;
import com.cosimba.dive.domain.clean.entity.CleanStatus;
import com.cosimba.dive.global.entity.TrashType;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CleanPostReponse {
    private Long id;
    private String cleanName;
    private String serialNumber;
    private LocalDateTime cleanDate;
    private String coastName;
    private double lat;
    private double lng;
    private Integer coastLength;
    private Integer collectBag;
    private Integer collectVal;
    private TrashType trashType;
    private CleanStatus cleanStatus;

    public static CleanPostReponse fromEntity(Clean clean) {
        return CleanPostReponse.builder()
                .id(clean.getId())
                .cleanName(clean.getCleanName())
                .serialNumber(clean.getSerialNum())
                .cleanDate(clean.getCleanDate())
                .coastName(clean.getCoastName())
                .lat(clean.getLat())
                .lng(clean.getLng())
                .coastLength(clean.getCoastLength())
                .collectBag(clean.getCollectBag())
                .collectVal(clean.getCollectVal())
                .trashType(clean.getTrashType())
                .cleanStatus(clean.getCleanStatus())
                .build();
    }
}

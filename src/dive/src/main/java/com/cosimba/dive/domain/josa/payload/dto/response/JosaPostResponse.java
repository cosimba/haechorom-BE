package com.cosimba.dive.domain.josa.payload.dto.response;

import com.cosimba.dive.domain.josa.entity.Josa;
import com.cosimba.dive.domain.josa.entity.JosaStatus;
import com.cosimba.dive.global.entity.TrashType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// 조사를 조회할 때 응답으로 사용하는 DTO
@Getter
@Builder
public class JosaPostResponse {
    private Long id;
    private String josaName;
    private String serialNumber;
    private LocalDateTime josaDate;
    private String coastName;
    private double lat;
    private double lng;
    private Integer coastLength;
    private Integer predictVal;
    private TrashType trashType;
    private JosaStatus josaStatus;

    // Josa 엔티티에서 DTO 로 변환, fromEntity() 메소드로 Josa 엔티티를 변환하여 응답 데이터를 구성
    public static JosaPostResponse fromEntity(Josa josa) {
        return JosaPostResponse.builder()
                .id(josa.getId())
                .josaName(josa.getJosaName())
                .serialNumber(josa.getSerialNumber())
                .josaDate(josa.getJosaDate())
                .coastName(josa.getCoastName())
                .lat(josa.getLat())
                .lng(josa.getLng())
                .coastLength(josa.getCoastLength())
                .predictVal(josa.getCollectBag())
                .trashType(josa.getTrashType())
                .josaStatus(josa.getJosaStatus())
                .build();
    }
}

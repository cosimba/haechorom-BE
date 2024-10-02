package com.cosimba.dive.domain.josa.payload.dto.request;

import com.cosimba.dive.domain.josa.entity.JosaStatus;
import com.cosimba.dive.global.entity.TrashType;

import java.time.LocalDateTime;

// 조사 정보를 생성하기 위한 요청 데이터를 담는 DTO
// 각 필드는 Josa 엔티티의 필드와 매핑
public record CreateJosaRequest(
        String josaName,
        String serialNumber,
        LocalDateTime josaDate,
        String coastName,
        double lat,
        double lng,
        Integer coastLength,
        Integer collectBag,
        TrashType trashType,
        JosaStatus josaStatus
) {
}

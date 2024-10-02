package com.cosimba.dive.domain.josa.payload.dto.request;

import com.cosimba.dive.domain.josa.entity.JosaStatus;
import com.cosimba.dive.global.entity.TrashType;

import java.time.LocalDateTime;

// 조사 정보를 수정하기 위한 DTO, 업데이트할 필드들이 포함
public record UpdateJosaRequest(
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

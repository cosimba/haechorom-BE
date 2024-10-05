package com.cosimba.dive.domain.clean.payload.dto.request;

import com.cosimba.dive.domain.clean.entity.CleanStatus;
import com.cosimba.dive.global.entity.TrashType;

import java.time.LocalDateTime;

public record UpdateCleanRequest(
        String cleanName,
        String serialNumber,
        LocalDateTime cleanDate,
        String coastName,
        double lat,
        double lng,
        String foreImg,
        String cleanImg,
        Integer coastLength,
        Integer collectBag,
        Integer collectVal,
        TrashType trashType,
        CleanStatus cleanStatus
) {
}

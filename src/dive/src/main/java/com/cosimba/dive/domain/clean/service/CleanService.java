package com.cosimba.dive.domain.clean.service;

import com.cosimba.dive.domain.clean.entity.Clean;
import com.cosimba.dive.domain.clean.payload.dto.request.CreateCleanRequest;
import com.cosimba.dive.domain.clean.payload.dto.request.UpdateCleanRequest;
import com.cosimba.dive.domain.clean.repository.CleanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CleanService {

    private final CleanRepository cleanRepository;

    @Transactional
    public void createClean(CreateCleanRequest request) {
        log.info("청소 등록 요청: {}", request);
        Clean clean = Clean.create(
                request.cleanName(),
                request.serialNumber(),
                request.cleanDate(),
                request.coastName(),
                request.lat(),
                request.lng(),
                request.coastLength(),
                request.collectBag(),
                request.collectVal(),
                request.trashType(),
                request.cleanStatus()
        );

        cleanRepository.save(clean);
        log.info("청소 등록 완료: {}", clean);
    }

    public void updateClean(UpdateCleanRequest request) {
        log.info("청소 수정 요청: {}", request);
        Clean clean = cleanRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("해당 청소 정보가 없습니다."));

        clean.update(
                request.cleanName(),
                request.serialNumber(),
                request.cleanDate(),
                request.coastName(),
                request.lat(),
                request.lng(),
                request.coastLength(),
                request.collectBag(),
                request.collectVal(),
                request.trashType(),
                request.cleanStatus()
        );

        log.info("청소 수정 완료: {}", clean);
    }
}

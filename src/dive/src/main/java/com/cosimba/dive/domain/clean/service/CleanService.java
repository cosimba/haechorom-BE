package com.cosimba.dive.domain.clean.service;

import com.cosimba.dive.domain.clean.entity.Clean;
import com.cosimba.dive.domain.clean.payload.dto.request.CreateCleanRequest;
import com.cosimba.dive.domain.clean.payload.dto.request.UpdateCleanRequest;
import com.cosimba.dive.domain.clean.payload.dto.response.CleanPostReponse;
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
        Clean clean = Clean.create(
                request.cleanName(),
                request.serialNumber(),
                request.josaId(),
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

    @Transactional
    public void updateClean(Long cleanId, UpdateCleanRequest request) {
        Clean clean = cleanRepository.findByIdAndIsDeletedIsFalse(cleanId)
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

    public CleanPostReponse viewCleanPost(Long josaId) {
        Clean clean = cleanRepository.findByJosaIdAndIsDeletedIsFalse(josaId)
                .orElseThrow(() -> new IllegalArgumentException("해당 청소 정보가 없습니다."));
        log.info("청소 조회 완료: {}", clean);
        return CleanPostReponse.fromEntity(clean);
    }

    @Transactional
    public void deleteClean(Long cleanId) {
        Clean clean = cleanRepository.findByIdAndIsDeletedIsFalse(cleanId)
                .orElseThrow(() -> new IllegalArgumentException("해당 청소 정보가 없습니다."));
        clean.delete();
        log.info("청소 삭제 완료: {}", clean);
    }
}

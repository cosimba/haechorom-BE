package com.cosimba.dive.domain.clean.service;

import com.cosimba.dive.domain.clean.entity.Clean;
import com.cosimba.dive.domain.clean.entity.CleanStatus;
import com.cosimba.dive.domain.clean.payload.dto.request.CreateCleanRequest;
import com.cosimba.dive.domain.clean.payload.dto.request.UpdateCleanRequest;
import com.cosimba.dive.domain.clean.payload.dto.response.CleanPostReponse;
import com.cosimba.dive.domain.clean.repository.CleanRepository;
import com.cosimba.dive.domain.josa.repository.JosaRepository;
import com.cosimba.dive.global.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CleanService {

    private final CleanRepository cleanRepository;
    private final JosaRepository josaRepository;

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
                request.foreImg(),
                request.cleanImg(),
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
                request.foreImg(),
                request.cleanImg(),
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

    public List<CleanPostReponse> viewCleanYetList(){
        List<Clean> cleanList = cleanRepository.findByCleanStatusAndIsDeletedIsFalse(CleanStatus.NOT_STARTED);
        log.info("청소 조회 완료: {}", cleanList);
        return cleanList.stream()
                .map(CleanPostReponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<CleanPostReponse> viewCleanFinList(){
        List<Clean> cleanList = cleanRepository.findByCleanStatusAndIsDeletedIsFalse(CleanStatus.CLEAN);
        log.info("청소 조회 완료: {}", cleanList);
        return cleanList.stream()
                .map(CleanPostReponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteClean(Long cleanId) {
        Clean clean = cleanRepository.findByIdAndIsDeletedIsFalse(cleanId)
                .orElseThrow(() -> new IllegalArgumentException("해당 청소 정보가 없습니다."));
        clean.delete();
        log.info("청소 삭제 완료: {}", clean);
    }

    @Transactional
    public void finishClean(Long cleanId) {
        Clean clean = cleanRepository.findByIdAndIsDeletedIsFalse(cleanId)
                .orElseThrow(() -> new IllegalArgumentException("해당 청소 정보가 없습니다."));
        clean.finish();
        log.info("청소 완료 처리 완료: {}", clean);
        josaRepository.findById(clean.getJosaId())
                .ifPresent(BaseEntity::delete);
    }

    @Transactional
    public void finishTransit(Long cleanId) {
        Clean clean = cleanRepository.findByIdAndIsDeletedIsFalse(cleanId)
                .orElseThrow(() -> new IllegalArgumentException("해당 청소 정보가 없습니다."));
        clean.delete();
        log.info("청소 완료 처리 완료: {}", clean);
    }
}

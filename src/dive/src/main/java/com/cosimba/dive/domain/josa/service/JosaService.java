package com.cosimba.dive.domain.josa.service;

import com.cosimba.dive.domain.josa.entity.Josa;
import com.cosimba.dive.domain.josa.payload.dto.request.CreateJosaRequest;
import com.cosimba.dive.domain.josa.payload.dto.request.UpdateJosaRequest;
import com.cosimba.dive.domain.josa.payload.dto.response.JosaPostResponse;
import com.cosimba.dive.domain.josa.repository.JosaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JosaService { // 실제 비즈니스 로직을 담당
    // 조사 정보를 생성, 수정, 조회, 삭제하는 기능이 포함

    private final JosaRepository josaRepository;

    // 조사 정보 생성
    @Transactional
    public void createJosa(CreateJosaRequest request) {
        Josa josa = Josa.create(
                request.josaName(),
                request.serialNumber(),
                request.josaDate(),
                request.coastName(),
                request.lat(),
                request.lng(),
                request.coastLength(),
                request.collectBag(),
                request.trashType(),
                request.josaStatus()
        );
        josaRepository.save(josa);
        log.info("조사 등록 완료: {}", josa);
    }

    // 조사 정보 수정
    @Transactional
    public void updateJosa(Long Id, UpdateJosaRequest request) {
        Josa josa = josaRepository.findByIdAndIsDeletedIsFalse(Id)
                .orElseThrow(() -> new IllegalArgumentException("해당 조사 정보가 없습니다."));

        josa.update(
                request.josaName(),
                request.serialNumber(),
                request.josaDate(),
                request.coastName(),
                request.lat(),
                request.lng(),
                request.coastLength(),
                request.collectBag(),
                request.trashType(),
                request.josaStatus()
        );
        log.info("조사 수정 완료: {}", josa);
    }

    // 조사 정보 조회
    public JosaPostResponse viewJosaPost(Long id) {
        Josa josa = josaRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 조사 정보가 없습니다."));
        log.info("조사 조회 완료: {}", josa);
        return JosaPostResponse.fromEntity(josa);
    }

    // 조사 정보 삭제
    @Transactional
    public void deleteJosa(Long id) {
        Josa josa = josaRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 조사 정보가 없습니다."));
        josa.delete();
        log.info("조사 삭제 완료: {}", josa);
    }
}

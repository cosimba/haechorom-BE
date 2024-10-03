package com.cosimba.dive_batch.reader;

import com.cosimba.dive.domain.clean.entity.Clean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TrashDataReader {

    private final EntityManager entityManager;

    // 모든 데이터를 한 번에 읽어오는 메서드
    public List<Clean> readAll() {
        LocalDateTime now = LocalDateTime.now(); // 현재 날짜와 시간
        LocalDateTime twentyFourHoursAgo = now.minusYears(7); // 7년 전 시간

        // JPQL을 사용해 데이터를 한 번에 가져옴
        TypedQuery<Clean> query = entityManager.createQuery(
                "SELECT ct FROM Clean ct WHERE ct.cleanDate >= :startDate AND ct.cleanDate < :endDate", Clean.class);

        // 파라미터 설정
        query.setParameter("startDate", twentyFourHoursAgo);
        query.setParameter("endDate", now.toLocalDate().plusYears(1).atStartOfDay());

        // 결과 리스트로 반환
        return query.getResultList();
    }
}


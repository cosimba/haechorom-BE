package com.cosimba.dive_batch.test;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import com.cosimba.dive_batch.domain.DailyTrashResult;
import com.cosimba.dive_batch.dto.AggregateResultDto;
import com.cosimba.dive_batch.dto.request.AggregateDateRequest;
import com.cosimba.dive_batch.repository.CustomDailyTrashResultRepository;
import com.cosimba.dive_batch.repository.DailyTrashResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EsTestService {

    private final DailyTrashResultRepository dailyTrashResultRepository;
    private final CustomDailyTrashResultRepository customDailyTrashResultRepository;

    @Transactional
    public void createDailyTrashResult(CreateDailyTrashResultRequest request) {
        DailyTrashResult dailyTrashResult = DailyTrashResult.create(
                request.coastalName(),
                request.totalCollected(),
                request.averageVal()
        );

        dailyTrashResultRepository.save(dailyTrashResult);
        log.info("일일 쓰레기 결과 등록 완료: {}", dailyTrashResult);
    }

    public DailyTrashResult getDailyTrashResult(Long id) {
        DailyTrashResult dailyTrashResult = dailyTrashResultRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 ID의 일일 쓰레기 결과가 없습니다."));
        log.info("일일 쓰레기 결과 조회: {}", dailyTrashResult.getCoastalName());
        return dailyTrashResult;
    }

    public List<AggregateResultDto> getDailyTrashResultByOneDay() throws Exception {
        List<AggregateResultDto> avgByCoastalName = customDailyTrashResultRepository.getAvgByCoastalName();
        return avgByCoastalName;
    }

    public List<AggregateResultDto> getAggregateResult(AggregateDateRequest request) throws Exception {
        LocalDateTime start = LocalDateTime.of(request.start().getYear(), request.start().getMonth(),
                request.start().getDay(), 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(request.end().getYear(), request.end().getMonth(),
                request.end().getDay(), 23, 59, 59);
        return customDailyTrashResultRepository.getAvgByCoastalNameByDate(start, end);
    }
}

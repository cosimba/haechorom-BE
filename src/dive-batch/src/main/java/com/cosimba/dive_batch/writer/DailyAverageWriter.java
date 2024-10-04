package com.cosimba.dive_batch.writer;

import com.cosimba.dive_batch.repository.AggregateResultRepository;
import com.cosimba.dive_batch.job.AggregatedResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class DailyAverageWriter implements StepExecutionListener {

    private final AggregateResultRepository aggregateResultRepository;
    private Map<String, Double> coastResults; // 결과를 저장할 필드 추가

    public void setCoastResults(Map<String, Double> coastResults) {
        this.coastResults = coastResults;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        // 필요한 초기화 코드가 있다면 여기에 추가
    }

    public void write() {
        log.info("집계된 결과를 저장합니다...");

        if (coastResults == null || coastResults.isEmpty()) {
            log.warn("저장할 집계 결과가 없습니다.");
            return; // 결과가 없으면 조기에 종료
        }

        // 저장할 결과 목록 준비
        List<AggregatedResult> resultsToSave = new ArrayList<>();

        for (String key : coastResults.keySet()) {
            if (key.endsWith("_total")) {
                String coastName = key.replace("_total", "");
                Double totalVal = coastResults.get(coastName + "_total");
                Double averageVal = coastResults.get(coastName + "_average");

                // 결과를 생성하고 목록에 추가
                AggregatedResult aggregatedResult = AggregatedResult.create(coastName, totalVal, averageVal);
                resultsToSave.add(aggregatedResult);
            }
        }

        // 모든 결과를 한 번에 저장
        aggregateResultRepository.saveAll(resultsToSave);
        log.info("{}개의 집계 결과를 저장했습니다.", resultsToSave.size());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        // 이전에 write()를 호출하여 저장했던 로직 제거
        log.info("Write step completed.");
        return ExitStatus.COMPLETED;
    }
}

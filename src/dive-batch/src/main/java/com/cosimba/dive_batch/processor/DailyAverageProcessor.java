package com.cosimba.dive_batch.processor;

import com.cosimba.dive.domain.clean.entity.Clean;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DailyAverageProcessor implements ItemProcessor<List<Clean>, Map<String, Double>>, StepExecutionListener {

    private final Map<String, Double> coastTotalMap = new HashMap<>();
    private final Map<String, Integer> coastCountMap = new HashMap<>();
    @Getter
    private final Map<String, Double> resultMap = new HashMap<>();  // 최종 결과를 저장할 맵

    @Override
    public Map<String, Double> process(List<Clean> cleanList) throws Exception {
        log.info("Processing list of size: {}", cleanList.size());

        // 1. Process the list and calculate totals and averages
        for (Clean clean : cleanList) {
            String coastName = clean.getCoastName();
            double collectVal = clean.getCollectVal(); // 수거량

            // Accumulate totals and counts by coast
            coastTotalMap.put(coastName, coastTotalMap.getOrDefault(coastName, 0.0) + collectVal);
            coastCountMap.put(coastName, coastCountMap.getOrDefault(coastName, 0) + 1);
        }

        // 2. Calculate and store totals and averages
        for (String coast : coastTotalMap.keySet()) {
            double totalVal = coastTotalMap.get(coast);
            int count = coastCountMap.get(coast);
            double average = totalVal / count;

            log.info("해안: {}, 총합: {}, 평균: {}", coast, totalVal, average);

            // 총합 및 평균을 맵에 저장
            resultMap.put(coast + "_total", totalVal);
            resultMap.put(coast + "_average", average);
        }

        return resultMap; // 최종 결과 반환
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        // Initialization before the step begins, if needed
        log.info("Starting the step");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        // No longer needed as processing logic is moved to process method
        return ExitStatus.COMPLETED;
    }

}

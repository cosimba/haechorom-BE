package com.cosimba.dive_batch.writer;

import com.cosimba.dive_batch.domain.DailyTrashResult;
import com.cosimba.dive_batch.repository.DailyTrashResultRepository;
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

    private final DailyTrashResultRepository dailyTrashResultRepository;
    private Map<String, Double> coastResults;

    public void setCoastResults(Map<String, Double> coastResults) {
        this.coastResults = coastResults;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        // Initialization code if needed
    }

    public void write() {
        log.info("Saving aggregated results...");

        if (coastResults == null || coastResults.isEmpty()) {
            log.warn("No aggregated results to save.");
            return; // Exit early if there are no results
        }

        // Prepare the results to save
        List<DailyTrashResult> resultsToSave = new ArrayList<>();

        for (String key : coastResults.keySet()) {
            if (key.endsWith("_total")) {
                String coastName = key.replace("_total", "");
                Double totalVal = coastResults.get(coastName + "_total");
                Double averageVal = coastResults.get(coastName + "_average");

                // Create result and add to the list
                DailyTrashResult dailyTrashResult = DailyTrashResult.create(coastName, totalVal, averageVal);
                resultsToSave.add(dailyTrashResult);
            }
        }

        // Save all results at once
        if (!resultsToSave.isEmpty()) {
            dailyTrashResultRepository.saveAll(resultsToSave);
            log.info("{} aggregated results saved.", resultsToSave.size());
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Write step completed.");
        return ExitStatus.COMPLETED;
    }
}

package com.cosimba.dive_batch.test;

import com.cosimba.dive_batch.domain.DailyTrashResult;
import com.cosimba.dive_batch.dto.AggregateResultDto;
import com.cosimba.dive_batch.dto.request.AggregateDateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/es/api/v1")
@RequiredArgsConstructor
@Validated
public class EsTestController {
    private final EsTestService esTestService;

    @PostMapping("/create")
    public void createDailyTrashResult(@RequestBody CreateDailyTrashResultRequest request) {
        esTestService.createDailyTrashResult(request);
    }

    @GetMapping("{id}")
    public DailyTrashResult getDailyTrashResult(@PathVariable Long id) {
        return esTestService.getDailyTrashResult(id);
    }

    @GetMapping("/one-day")
    public ResponseEntity<List<AggregateResultDto>> getDailyTrashResultByOneDay() throws Exception {
        return ResponseEntity.ok(esTestService.getDailyTrashResultByOneDay());
    }

    @PostMapping("/aggregate")
    public ResponseEntity<List<AggregateResultDto>> getAggregateResult(@RequestBody AggregateDateRequest request) throws Exception {
        return ResponseEntity.ok(esTestService.getAggregateResult(request));
    }
}

package com.cosimba.dive.domain.clean.controller;

import com.cosimba.dive.domain.clean.payload.dto.request.CreateCleanRequest;
import com.cosimba.dive.domain.clean.service.CleanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clean/api/v1")
@RequiredArgsConstructor
@Validated
public class CleanController {

    private final CleanService cleanService;

    public ResponseEntity<Void> createClean(CreateCleanRequest request) {
        cleanService.createClean(request);
        return ResponseEntity.ok().build();
    }
}

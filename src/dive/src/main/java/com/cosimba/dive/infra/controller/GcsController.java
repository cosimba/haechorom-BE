package com.cosimba.dive.infra.controller;

import com.cosimba.dive.infra.service.GcsService;
import com.google.cloud.storage.Blob;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gcs/api/v1")
@RequiredArgsConstructor
@Validated
public class GcsController {
    private final GcsService gcsService;

    @PostMapping("/signed-url")
    public ResponseEntity<String> generateSignedUrl(String objectName) {
        return ResponseEntity.ok(
                gcsService.generateSignedUrl(objectName)
        );
    }

    @PostMapping("/download")
    public ResponseEntity<String> downloadImage(String objectName) {
        return ResponseEntity.ok()
                .body(
                        gcsService.downloadImage(objectName)
                );
    }
}

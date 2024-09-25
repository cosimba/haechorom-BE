package com.cosimba.dive_batch.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/api/v1")
@RequiredArgsConstructor
@Validated
public class TestController {

    private final ESTest esTest;

    @GetMapping("/test")
    public ResponseEntity<?> test() throws Exception {
        esTest.read();
        return ResponseEntity.ok().build();
    }
}

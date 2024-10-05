package com.cosimba.dive.domain.josa.controller;

import com.cosimba.dive.domain.josa.entity.Josa;
import com.cosimba.dive.domain.josa.payload.dto.request.CreateJosaRequest;
import com.cosimba.dive.domain.josa.payload.dto.request.UpdateJosaRequest;
import com.cosimba.dive.domain.josa.payload.dto.response.JosaPostResponse;
import com.cosimba.dive.domain.josa.service.JosaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/josa/api/v1")
@RequiredArgsConstructor
@Validated
public class JosaController {
    // API 를 정의하는 컨트롤러, 조사 데이터를 생성, 수정, 조회, 삭제하는 API 를 구현

    private final JosaService josaService;

    // 조사 생성
    @PostMapping("/create")
    public ResponseEntity<Void> createJosa(@RequestBody CreateJosaRequest request) {
        josaService.createJosa(request);
        return ResponseEntity.ok().build();
    }

    // 조사 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateJosa(@PathVariable("id") Long id, @RequestBody UpdateJosaRequest request) {
        josaService.updateJosa(id, request);
        return ResponseEntity.ok().build();
    }

    // 조사 조회
    @GetMapping("/search/{id}")
    public ResponseEntity<JosaPostResponse> viewJosaPost(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                josaService.viewJosaPost(id)
        );
    }

    // 모든 is_deleted = false 조사 내역 조회
    @GetMapping("/search/all")
    public ResponseEntity<List<Josa>> getAllActiveJosas() {
        List<Josa> activeJosas = josaService.findAllActiveJosas();
        return ResponseEntity.ok(activeJosas);
    }

    // 조사 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteJosa(@PathVariable("id") Long id) {
        josaService.deleteJosa(id);
        return ResponseEntity.ok().build();
    }


}

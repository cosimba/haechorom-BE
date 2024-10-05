package com.cosimba.dive.domain.clean.controller;

import com.cosimba.dive.domain.clean.payload.dto.request.CreateCleanRequest;
import com.cosimba.dive.domain.clean.payload.dto.request.UpdateCleanRequest;
import com.cosimba.dive.domain.clean.payload.dto.response.CleanPostReponse;
import com.cosimba.dive.domain.clean.service.CleanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clean/api/v1")
@RequiredArgsConstructor
public class CleanController {

    private final CleanService cleanService;

    @PostMapping("/create")
    public ResponseEntity<Void> createClean(@RequestBody CreateCleanRequest request) {
        cleanService.createClean(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{cleanId}")
    public ResponseEntity<Void> updateClean(@PathVariable Long cleanId, @RequestBody UpdateCleanRequest request) {
        cleanService.updateClean(cleanId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{josaId}")
    public ResponseEntity<CleanPostReponse> viewCleanPost(@PathVariable Long josaId) {
        return ResponseEntity.ok(
                cleanService.viewCleanPost(josaId)
        );
    }

    @GetMapping("/view/cleanyet")
    public ResponseEntity<List<CleanPostReponse>> viewCleanYetList(){
        return ResponseEntity.ok(
                cleanService.viewCleanYetList()
        );
    }

    @GetMapping("/view/cleanfin")
    public ResponseEntity<List<CleanPostReponse>> viewCleanFinList(){
        return ResponseEntity.ok(
                cleanService.viewCleanFinList()
        );
    }

    @DeleteMapping("/{cleanId}")
    public ResponseEntity<Void> deleteClean(@PathVariable Long cleanId) {
        cleanService.deleteClean(cleanId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/finish/{cleanId}")
    public ResponseEntity<Void> finishClean(@PathVariable Long cleanId) {
        cleanService.finishClean(cleanId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fintransit/{cleanId}")
    public ResponseEntity<Void> finishTransit(@PathVariable Long cleanId) {
        cleanService.finishTransit(cleanId);
        return ResponseEntity.ok().build();
    }
}

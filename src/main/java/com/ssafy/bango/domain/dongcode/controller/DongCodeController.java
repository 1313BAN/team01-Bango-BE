package com.ssafy.bango.domain.dongcode.controller;

import com.ssafy.bango.domain.dongcode.entity.DongCode;
import com.ssafy.bango.domain.dongcode.service.DongCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dongcode")
@RequiredArgsConstructor
public class DongCodeController implements DongCodeApi {
    private final DongCodeService dongCodeService;

    @GetMapping("/sido")
    public ResponseEntity<List<String>> getDistinctSido() {
        return ResponseEntity.ok(dongCodeService.getDistinctSido());
    }

    @GetMapping("/gugun")
    public ResponseEntity<List<String>> getDistinctGugun(String sidoName) {
        return ResponseEntity.ok(dongCodeService.getDistinctGugun(sidoName));
    }

    @GetMapping("/dong")
    public ResponseEntity<List<DongCode>> getDongCode(String sidoName, String gugunName) {
        return ResponseEntity.ok(dongCodeService.getDongCode(sidoName, gugunName));
    }
}



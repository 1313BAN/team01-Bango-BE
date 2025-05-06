package com.ssafy.ssafyhome.domain.dongcode.controller;

import com.ssafy.ssafyhome.domain.dongcode.dto.DongCodeDTO;
import com.ssafy.ssafyhome.domain.dongcode.service.DongCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dongcode")
@RequiredArgsConstructor
public class DongCodeController implements DongCodeApi {
    private final DongCodeService dongCodeService;

    @GetMapping("/sido")
    public ResponseEntity<List<DongCodeDTO>> getDistinctSido() {
        return ResponseEntity.ok(dongCodeService.getDistinctSido());
    }

    @GetMapping("/gugun")
    public ResponseEntity<List<DongCodeDTO>> getDistinctGugun(String sidoName) {
        return ResponseEntity.ok(dongCodeService.getDistinctGugun(sidoName));
    }

    @GetMapping("/dong")
    public ResponseEntity<List<DongCodeDTO>> getDongCode(String sidoName, String gugunName) {
        return ResponseEntity.ok(dongCodeService.getDongCode(sidoName, gugunName));
    }
}



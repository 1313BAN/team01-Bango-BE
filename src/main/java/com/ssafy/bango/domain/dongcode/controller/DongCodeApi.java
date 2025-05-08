package com.ssafy.bango.domain.dongcode.controller;

import com.ssafy.bango.domain.dongcode.dto.DongCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface DongCodeApi {
    ResponseEntity<List<String>> getDistinctSido();
    ResponseEntity<List<String>> getDistinctGugun(@RequestParam String sidoName);
    ResponseEntity<List<DongCode>> getDongCode(@RequestParam String sidoName, @RequestParam String gugunName);
}

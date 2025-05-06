package com.ssafy.ssafyhome.domain.dongcode.controller;

import com.ssafy.ssafyhome.domain.dongcode.dto.DongCodeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface DongCodeApi {
    ResponseEntity<List<DongCodeDTO>> getDistinctSido();
    ResponseEntity<List<DongCodeDTO>> getDistinctGugun(@RequestParam String sidoName);
    ResponseEntity<List<DongCodeDTO>> getDongCode(@RequestParam String sidoName, @RequestParam String gugunName);
}

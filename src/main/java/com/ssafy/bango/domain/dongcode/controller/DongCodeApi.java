package com.ssafy.bango.domain.dongcode.controller;

import com.ssafy.bango.domain.dongcode.entity.DongCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "동 코드 컨트롤러", description = "동 코드 관련 API입니다.")
public interface DongCodeApi {
    @Operation(summary = "시도 조회", description = "모든 시도를 조회하는 API 입니다.", responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "시도 조회 완료")
    })
    ResponseEntity<List<String>> getDistinctSido();

    @Operation(summary = "구군 조회", description = "모든 구군을 조회하는 API 입니다.", responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "구군 조회 완료")
    })
    ResponseEntity<List<String>> getDistinctGugun(@RequestParam String sidoName);

    @Operation(summary = "동 조회", description = "모든 동을 조회하는 API 입니다.", responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "동 조회 완료")
    })
    ResponseEntity<List<DongCode>> getDongCode(@RequestParam String sidoName, @RequestParam String gugunName);
}

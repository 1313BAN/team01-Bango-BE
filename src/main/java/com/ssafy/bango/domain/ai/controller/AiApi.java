package com.ssafy.bango.domain.ai.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "스프링 AI 컨트롤러", description = "스프링 AI 관련 API입니다.")
public interface AiApi {
    @Operation(summary = "AI로 매물 주변 시설 정보 조회", description = "매물 주변 시설 정보를 조회하는 API 입니다.", responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "매물 주변 시설 정보 조회 완료")
    })
    Object getFacilityInfo(@PathVariable int houseId);
}

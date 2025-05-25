package com.ssafy.bango.domain.rentalhouse.controller;

import com.ssafy.bango.domain.rentalhouse.dto.response.DongSummaryResponse;
import com.ssafy.bango.domain.rentalhouse.dto.response.GugunSummaryResponse;
import com.ssafy.bango.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "임대 주택 summary 컨트롤러", description = "임대 주택 summary 관련 API입니다.")
public interface RentalHouseSummaryApi {
    @Operation(summary = "구군별 임대 주택 summary 조회", description = "구군별 임대 주택 개수 및 위경도 조회하는 API 입니다.", responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "구군별 임대 주택 summary 조회 완료")
    })
    ApiResponse<List<GugunSummaryResponse>> getGugunSummary();

    @Operation(summary = "동별 임대 주택 summary 조회", description = "동별 임대 주택 개수 및 위경도 조회하는 API 입니다.", responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "동별 임대 주택 summary 조회 완료")
    })
    ApiResponse<List<DongSummaryResponse>> getDongSummary();
}

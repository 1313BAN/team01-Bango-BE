package com.ssafy.bango.domain.rentalhouse.controller;

import com.ssafy.bango.domain.rentalhouse.dto.request.DongCodeRequest;
import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "임대 주택 컨트롤러", description = "임대 주택 관련 API입니다.")
public interface RentalHouseApi {
    @Operation(summary = "모든 임대 주택 조회", description = "모든 임대 주택을 조회하는 API 입니다.", responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "임대 주택 조회 완료")
    })
    ApiResponse<List<RentalHouse>> getRentalHouses();

    @Operation(summary = "동별 임대 주택 조회", description = "동별 임대 주택을 조회하는 API 입니다.", responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "동별 주택 조회 완료")
    })
    ApiResponse<List<RentalHouse>> getRentalHousesByRegion(@Valid @ModelAttribute DongCodeRequest request);

    @Operation(summary = "특정 임대 주택 조회", description = "특정 임대 주택을 조회하는 API 입니다.", responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "특정 주택 조회 완료")
    })
    ApiResponse<RentalHouse> getRentalHouse(@PathVariable int houseId);
}

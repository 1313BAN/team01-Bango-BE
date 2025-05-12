package com.ssafy.bango.domain.rentalhouse.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DongCodeRequest {
    @NotNull(message = "동 코드는 null일 수 없습니다.")
    @Size(min = 10, max = 10, message = "동 코드는 정확히 10자리여야 합니다.")
    private String dongCode;
}

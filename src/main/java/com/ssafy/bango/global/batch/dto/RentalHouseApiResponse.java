package com.ssafy.bango.global.batch.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalHouseApiResponse {
    private Long hsmpSn;
    private String insttNm;
    private String brtcCode;
    private String brtcNm;
    private String signguCode;
    private String signguNm;
    private String rnAdres;
    private String pnu;
    private String competDe;
    private Integer hshldCo;
    private String suplyTyNm;
    private String styleNm;
    private Double suplyPrvuseAr;
    private Double suplyCmnuseAr;
    private String houseTyNm;
    private String buldStleNm;
    private String elvtrInstlAtNm;
    private Integer parkngCo;
    private Integer bassRentGtn;
    private Integer bassMtRntchrg;
    private Integer bassCnvrsGtnLmt;
}
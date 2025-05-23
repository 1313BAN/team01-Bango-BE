package com.ssafy.bango.domain.rentalnotice.feign.dto.response;

import com.ssafy.bango.domain.rentalnotice.entity.RentalNotice;
import com.ssafy.bango.global.util.DateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Item {
    private String pblancId;
    private int houseSn;
    private String sttusNm;
    private String pblancNm;
    private String suplyInsttNm;
    private String houseTyNm;
    private String suplyTyNm;
    private String beforePblancId;
    private String rcritPblancDe;
    private String przwnerPresnatnDe;
    private String suplyHoCo;
    private String refrnc;
    private String url;
    private String pcUrl;
    private String mobileUrl;
    private String hsmpNm;
    private String brtcNm;
    private String signguNm;
    private String fullAdres;
    private String rnCodeNm;
    private String refrnLegaldongNm;
    private String pnu;
    private String heatMthdNm;
    private String totHshldCo;
    private int sumSuplyCo;
    private int rentGtn;
    private int enty;
    private int prtpay;
    private int surlus;
    private int mtRntchrg;
    private String beginDe;
    private String endDe;

    public RentalNotice toEntity() {
        return RentalNotice.builder()
                .noticeName(pblancNm)
                .pblancId(pblancId)
                .institutionType(suplyInsttNm)
                .status(sttusNm)
                .houseType(houseTyNm)
                .supplyType(suplyTyNm)
                .createdDate(DateUtil.parseYyyyMMdd(rcritPblancDe))
                .announceDate(DateUtil.parseYyyyMMdd(przwnerPresnatnDe))
                .supplyHoCount(suplyHoCo)
                .contactInfo(refrnc)
                .institutionUrl(url)
                .houseName(hsmpNm)
                .sidoName(brtcNm)
                .gugunName(signguNm)
                .pnu(pnu)
                .beginDate(DateUtil.parseYyyyMMdd(beginDe))
                .endDate(DateUtil.parseYyyyMMdd(endDe))
                .build();
    }
}

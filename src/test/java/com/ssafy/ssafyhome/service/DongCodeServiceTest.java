package com.ssafy.ssafyhome.service;

import com.ssafy.ssafyhome.domain.dongcode.dto.DongCodeDTO;
import com.ssafy.ssafyhome.domain.dongcode.service.DongCodeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DongCodeServiceTest {

    @Autowired
    private DongCodeService dongCodeService;

    @Test
    @DisplayName("시도 가져오기 테스트")
    public void getDistinctSidoTest() {
        List<DongCodeDTO> distinctSido = dongCodeService.getDistinctSido();

        Assertions.assertEquals(17, distinctSido.size());
    }

    @Test
    @DisplayName("구군 가져오기 테스트")
    public void getDistinctGugunTest() {
        String sidoName = "서울특별시";
        List<DongCodeDTO> distinctGugun = dongCodeService.getDistinctGugun(sidoName);

        for (DongCodeDTO d : distinctGugun) {
            System.out.println(d);
        }

        Assertions.assertEquals(25, distinctGugun.size());
        Assertions.assertEquals(distinctGugun.get(0).getGugunName(), "종로구");
    }

    @Test
    @DisplayName("동 목록 가져오기 테스트")
    public void getDongCodeTest() {
        String sidoName = "서울특별시";
        String gugunName = "종로구";
        List<DongCodeDTO> dongcodes = dongCodeService.getDongCode(sidoName, gugunName);

        Assertions.assertEquals(87, dongcodes.size());
    }
}

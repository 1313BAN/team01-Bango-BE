package com.ssafy.bango.domain.dongcode.dao;

import com.ssafy.bango.domain.dongcode.dto.DongCode;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DongCodeDAO extends JpaRepository<DongCode, String> {
    @Query("SELECT DISTINCT d.sidoName FROM DongCode d")
    List<String> getDistinctSidoNames();
    @Query("SELECT DISTINCT d.gugunName FROM DongCode d WHERE d.sidoName = :sidoName AND d.gugunName IS NOT NULL ORDER BY d.gugunName")
    List<String> getDistinctGugunBySidoName(String sidoName);
    List<DongCode> findBySidoNameAndGugunNameAndDongNameIsNotNullOrderByDongName(String sidoName, String gugunName);

    @Query("SELECT DISTINCT d.dongCode FROM DongCode d WHERE d.dongName = :dongName")
    String findDongCodeByDongName(@Param("dongName") String dongName);
}

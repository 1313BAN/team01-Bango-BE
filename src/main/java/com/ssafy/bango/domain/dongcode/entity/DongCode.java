package com.ssafy.bango.domain.dongcode.entity;

import com.ssafy.bango.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(
    indexes = {
        @Index(name = "idx_dong_code", columnList = "dongCode")
    }
)
public class DongCode extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String dongCode;

    @Column(length = 30)
    private String sidoName;

    @Column(length = 30)
    private String gugunName;

    @Column(length = 30)
    private String dongName;
}

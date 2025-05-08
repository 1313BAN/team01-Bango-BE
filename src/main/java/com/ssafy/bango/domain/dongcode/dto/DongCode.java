package com.ssafy.bango.domain.dongcode.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DongCode {
    @Id
    @Column(length = 10)
    private String dongcode;

    @Column(length = 30)
    private String sidoName;

    @Column(length = 30)
    private String gugunName;

    @Column(length = 30)
    private String dongName;
}

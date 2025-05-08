package com.ssafy.bango.domain.dongcode.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DongCode {
    @Id
    public String dongCode;
    public String sidoName;
    public String gugunName;
    public String dongName;
}

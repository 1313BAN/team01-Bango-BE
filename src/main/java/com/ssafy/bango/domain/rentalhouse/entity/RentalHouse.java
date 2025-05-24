package com.ssafy.bango.domain.rentalhouse.entity;

import com.ssafy.bango.domain.rentalhouse.dto.response.GeoPointResponse;
import com.ssafy.bango.global.batch.dto.RentalHouseApiResponse;
import com.ssafy.bango.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalHouse extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer houseId;

  @Column(name = "institude_type", length = 45)
  private String institudeType;

  private Integer sidoCode;

  @Column(length = 30)
  private String sidoName;

  private Integer gugunCode;

  @Column(length = 30)
  private String gugunName;

  @Column(length = 255)
  private String address;

  @Column(length = 20)
  private String pnu;

  private LocalDateTime builtAt;

  @Column(length = 30)
  private String supplyType;

  @Column(length = 30)
  private String houseType;

  @Column(length = 30)
  private String hasElevator;

  @Column(length = 30)
  private String parkingCount;

  @Column(length = 30)
  private double latitude;

  @Column(length = 30)
  private double longitude;

  @OneToMany(mappedBy = "rentalHouse", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<RentalHouseStyle> styles;

  @OneToMany(mappedBy = "rentalHouse", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Facility> facilities;

  public static RentalHouse from(RentalHouseApiResponse dto, GeoPointResponse geo) {
    RentalHouse house = RentalHouse.builder()
        .institudeType(dto.getInsttNm())
        .sidoCode(convertToInteger(dto.getBrtcCode()))
        .sidoName(dto.getBrtcNm())
        .gugunCode(convertToInteger(dto.getSignguCode()))
        .gugunName(dto.getSignguNm())
        .address(dto.getRnAdres())
        .pnu(dto.getPnu())
        .builtAt(parseDate(dto.getCompetDe()))
        .supplyType(dto.getSuplyTyNm())
        .houseType(dto.getHouseTyNm())
        .hasElevator(dto.getElvtrInstlAtNm())
        .parkingCount(dto.getParkngCo() != null ? dto.getParkngCo().toString() : null)
        .latitude(geo != null ? geo.latitude() : 0)
        .longitude(geo != null ? geo.longitude() : 0)
        .build();

    house.styles = new ArrayList<>();
    RentalHouseStyle style = RentalHouseStyle.from(dto, house);
    house.styles.add(style);
    return house;
  }

  private static Integer convertToInteger(String s) {
    try {
      return s != null ? Integer.parseInt(s) : null;
    } catch (NumberFormatException e) {
      return null;
    }
  }

  private static LocalDateTime parseDate(String dateString) {
    try {
      return dateString != null ? LocalDate.parse(dateString, DateTimeFormatter.BASIC_ISO_DATE).atStartOfDay() : null;
    } catch (Exception e) {
      return null;
    }
  }
}
package com.ssafy.bango.domain.rentalhouse.service;

import static com.ssafy.bango.global.exception.enums.ErrorType.JSON_PARSING_ERROR;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.bango.domain.rentalhouse.dto.request.FacilityRequest;
import com.ssafy.bango.domain.rentalhouse.entity.enums.FacilityType;
import com.ssafy.bango.domain.rentalhouse.entity.Facility;
import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.repository.FacilityRepository;
import com.ssafy.bango.domain.rentalhouse.repository.RentalHouseRepository;
import com.ssafy.bango.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoFacilityService {
    private static final String KAKAO_API_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";
    private static final String HEADER_AUTH_PREFIX = "KakaoAK ";
    private static final String QUERY_PARAM_KEY = "query";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final Integer RADIUS = 8000;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final FacilityRepository facilityRepository;
    private final RentalHouseService rentalHouseService;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Transactional
    public ResponseEntity<Void> saveClosestFacilities(FacilityRequest request) {
        HttpEntity<?> entity = buildHttpEntity();

        for (FacilityType type : FacilityType.values()) {
            String url = buildUrl(type.getDescription(), request.getX(), request.getY());

            try {
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                saveClosestFacility(type, jsonResponse, request.getHouseId());
            } catch (CustomException e) {
                throw e;
            } catch (Exception e) {
                log.error("Failed to parse Kakao API response", e);
                throw new CustomException(JSON_PARSING_ERROR);
            }
        }
        return ResponseEntity.noContent().build();
    }

    private String buildUrl(String type, String x, String y) {
        return UriComponentsBuilder.fromUriString(KAKAO_API_URL)
            .queryParam(QUERY_PARAM_KEY, type)
            .queryParam("x", x)
            .queryParam("y", y)
            .queryParam("radius", RADIUS)
            .queryParam("sort", "distance")
            .queryParam("size", 1)
            .build()
            .toUriString();
    }

    private HttpEntity<?> buildHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));
        headers.set(HEADER_AUTHORIZATION, HEADER_AUTH_PREFIX + kakaoApiKey);
        return new HttpEntity<>(headers);
    }

    private void saveClosestFacility(FacilityType type, JsonNode jsonResponse, Integer houseId) {
        JsonNode documents = jsonResponse.path("documents");

        if (documents.isArray() && !documents.isEmpty()) {
            RentalHouse house = rentalHouseService.getRentalHouseByHouseId(houseId);
            JsonNode closestFacility = documents.get(0);

            Facility facility = Facility.builder()
                .rentalHouse(house)
                .type(type)
                .distance(closestFacility.path("distance").asInt())
                .build();

            facilityRepository.save(facility);
            log.info("Saved facility: {} at distance {} meters", type, facility.getDistance());
        }
    }
}

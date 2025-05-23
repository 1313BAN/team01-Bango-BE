package com.ssafy.bango.global.batch.chunk.facility.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.bango.domain.rentalhouse.entity.Facility;
import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.entity.enums.FacilityType;
import com.ssafy.bango.global.batch.dto.v1.FacilityApiResponse;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
public class FacilityApiItemProcessor implements ItemProcessor<RentalHouse, List<Facility>> {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final String openApiUrl;
    private final String openApiServiceKey;
    private static final int RADIUS = 8000;

    @Override
    public List<Facility> process(RentalHouse rentalHouse) throws Exception {
        List<Facility> facilities = new ArrayList<>();
        HttpEntity<?> entity = buildHttpEntity();

        for (FacilityType type : FacilityType.values()) {
            try {
                ResponseEntity<String> response = fetchFacility(entity, rentalHouse, type);

                if (response.getStatusCode().is2xxSuccessful()) {
                    FacilityApiResponse result = parseResponse(response.getBody());
                    Facility facility = makeFacility(result, rentalHouse, type);
                    if (facility != null) facilities.add(facility);
                }
            } catch (Exception e) {
                log.error("시설 정보 조회 중 오류 발생: type={}, rentalHouse={}", type.getDescription(), rentalHouse.getPnu(), e);
            }
        }
        return facilities;
    }

    private HttpEntity<?> buildHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + openApiServiceKey);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }

    private ResponseEntity<String> fetchFacility(HttpEntity<?> entity, RentalHouse rentalHouse, FacilityType type) throws URISyntaxException, MalformedURLException {
        URL url = buildUrl(type.getDescription(), rentalHouse.getLongitude(), rentalHouse.getLatitude());
        ResponseEntity<String> response = restTemplate.exchange(url.toURI(), HttpMethod.GET,entity, String.class);
        log.debug("API 호출 성공: type={}, status={}", type.getDescription(), response.getStatusCode());
        return response;
    }

    private URL buildUrl(String query, String x, String y) throws MalformedURLException {
        String url = UriComponentsBuilder.fromUriString(openApiUrl)
            .queryParam("query", query)
            .queryParam("x", x)
            .queryParam("y", y)
            .queryParam("radius", RADIUS)
            .queryParam("sort", "distance")
            .queryParam("size", 1)
            .toUriString();
        return new URL(url);
    }

    private FacilityApiResponse parseResponse(String body) throws JsonProcessingException {
        return objectMapper
            .readerFor(FacilityApiResponse.class)
            .readValue(body);
    }

    private Facility makeFacility(FacilityApiResponse result, RentalHouse rentalHouse, FacilityType type) {
        List<FacilityApiResponse.Document> docs = result.getDocuments();
        if (!docs.isEmpty()) {
            FacilityApiResponse.Document closest = docs.get(0);
            int distance = Integer.parseInt(closest.getDistance());

            log.info("가장 가까운 시설명: " + closest.getPlaceName());
            log.info("거리: " + closest.getDistance() + "m");
            return Facility.builder()
                .rentalHouse(rentalHouse)
                .type(type)
                .distance(distance)
                .build();
        }
        return null;
    }
}
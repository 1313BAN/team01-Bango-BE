package com.ssafy.bango.domain.rentalhouse.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.bango.domain.rentalhouse.dto.response.GeoPointResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeocodingService {

    private final RestTemplate restTemplate;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    public Optional<GeoPointResponse> getGeoFromAddress(String address) {
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl("https://dapi.kakao.com/v2/local/search/address.json")
                    .queryParam("query", address)
                    .build().toUriString();

            log.info("Requesting geocode for address: {}", address);
            log.debug("Constructed URL: {}", url);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            headers.set("Authorization", "KakaoAK " + kakaoApiKey);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            log.info("Geocode API response status: {}", response.getStatusCode());
            log.debug("Geocode API response body: {}", response.getBody());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            if (root.has("documents") && root.get("documents").isArray() && root.get("documents").size() > 0) {
                JsonNode location = root.get("documents").get(0);
                String lat = location.get("y").asText();
                String lng = location.get("x").asText();
                log.info("Extracted coordinates - lat: {}, lng: {}", lat, lng);

                return Optional.of(new GeoPointResponse(lat, lng));
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching geocode for address: {}", address, e);
        }
        return Optional.empty();
    }
}

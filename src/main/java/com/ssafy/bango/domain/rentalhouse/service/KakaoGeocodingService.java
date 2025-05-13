package com.ssafy.bango.domain.rentalhouse.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.bango.domain.rentalhouse.dto.response.GeoPointResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoGeocodingService {
    private static final String KAKAO_API_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    private static final String HEADER_AUTH_PREFIX = "KakaoAK ";
    private static final String QUERY_PARAM_KEY = "query";

    private final RestTemplate restTemplate;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    public Optional<GeoPointResponse> getGeoFromAddress(String address) {
        String url = buildUrl(address);
        HttpEntity<?> entity = buildHttpEntity();

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return parseCoordinates(response.getBody());
        } catch (Exception e) {
            log.error("Error occurred while fetching geocode for address: {}", address, e);
            return Optional.empty();
        }
    }

    private String buildUrl(String address) {
        return UriComponentsBuilder.fromUriString(KAKAO_API_URL)
            .queryParam(QUERY_PARAM_KEY, address)
            .build()
            .toUriString();
    }

    private HttpEntity<?> buildHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));
        headers.set(HttpHeaders.AUTHORIZATION, HEADER_AUTH_PREFIX + kakaoApiKey);
        return new HttpEntity<>(headers);
    }

    private Optional<GeoPointResponse> parseCoordinates(String responseBody) throws JsonProcessingException {
        JsonNode root = new ObjectMapper().readTree(responseBody);
        JsonNode documents = root.path("documents");

        if (!documents.isArray() || documents.isEmpty()) return Optional.empty();

        JsonNode location = documents.path(0);
        String lat = location.get("y").asText();
        String lng = location.get("x").asText();
        return Optional.of(new GeoPointResponse(lat, lng));
    }
}

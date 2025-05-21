package com.ssafy.bango.global.batch.chunk.rentalhouse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.bango.domain.rentalhouse.entity.enums.RentalHouseEnums;
import com.ssafy.bango.global.batch.dto.RentalHouseApiResponse;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@Slf4j
@StepScope
@RequiredArgsConstructor
@AllArgsConstructor
public class RentalHouseApiItemReader implements ItemReader<List<RentalHouseApiResponse>> {
    private final String openApiUrl;
    private final String openApiServiceKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final RentalHouseEnums.SignguCode[] codes;
    private int index = 0;

    @Override
    public List<RentalHouseApiResponse> read()
        throws UnexpectedInputException, ParseException, NonTransientResourceException, IOException, URISyntaxException {

        log.info(">>> reader called");
        return requestOpenApi();
    }

    private List<RentalHouseApiResponse> requestOpenApi() throws IOException, URISyntaxException {
        if (index >= codes.length) {
            return null;
        }
        String signguCode = codes[index++].getCode();

        URL url = buildUrl(signguCode);
        HttpEntity<?> entity = buildHttpEntity();

        ResponseEntity<String> response = restTemplate.exchange(url.toURI(), HttpMethod.GET, entity, String.class);
        log.info("API StatusCode: {}", response.getStatusCode());
        log.info("API Response: {}", response.getBody());

        if (response.getStatusCode().is2xxSuccessful()) {
            String jsonString = response.getBody();
            return parseResponse(jsonString);
        } else {
            throw new IOException("Failed to fetch data from API: " + response.getStatusCode());
        }
    }

    private URL buildUrl(String signguCode) throws MalformedURLException {
        String url = UriComponentsBuilder.fromUriString(openApiUrl)
                .queryParam("serviceKey", openApiServiceKey)
                .queryParam("brtcCode", 11)
                .queryParam("signguCode", signguCode)
                .queryParam("numOfRows", 5000)
                .queryParam("pageNo", 1)
                .build()
                .toUriString();
        return new URL(url);
    }

    private HttpEntity<?> buildHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }

    private List<RentalHouseApiResponse> parseResponse(String responseBody) throws JsonProcessingException {
        List<RentalHouseApiResponse> rentalHouseResponseList = new ArrayList<>();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Java 클래스에 정의되어 있지 않은 필드가 있어도 무시

        JsonNode root = objectMapper.readTree(responseBody);
        JsonNode itemListNode = root
            .path("response")
            .path("body")
            .path("item");
        for (JsonNode node : itemListNode) {
            rentalHouseResponseList.add(objectMapper.treeToValue(node, RentalHouseApiResponse.class));
        }
        log.info("rentalHouseResponseList: {}", rentalHouseResponseList.toString());
        return rentalHouseResponseList;
    }
}

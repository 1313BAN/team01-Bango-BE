package com.ssafy.bango.global.batch.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.entity.enums.RentalHouseEnums;
import com.ssafy.bango.domain.rentalhouse.repository.RentalHouseRepository;
import com.ssafy.bango.domain.rentalhouse.service.KakaoGeocodingService;
import com.ssafy.bango.global.batch.chunk.rentalhouse.RentalHouseApiItemProcessor;
import com.ssafy.bango.global.batch.chunk.rentalhouse.RentalHouseApiItemReader;
import com.ssafy.bango.global.batch.chunk.rentalhouse.RentalHouseApiItemWriter;
import com.ssafy.bango.global.batch.dto.RentalHouseApiResponse;
import com.ssafy.bango.global.batch.job.listener.CustomStepExecutionListener;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RentalHouseChunkConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final RentalHouseRepository rentalHouseRepository;
    private final KakaoGeocodingService kakaoGeocodingService;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final CustomStepExecutionListener listener;

    @Bean
    public Step fetchStep(
        ItemReader<List<RentalHouseApiResponse>> reader,
        ItemProcessor<List<RentalHouseApiResponse>, List<RentalHouse>> processor,
        ItemWriter<List<RentalHouse>> writer
    ) {
        return new StepBuilder("fetchStep", jobRepository)
            .<List<RentalHouseApiResponse>, List<RentalHouse>>chunk(1, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .listener(listener)
            .build();
    }

    @Bean
    @StepScope
    public ItemReader<List<RentalHouseApiResponse>> reader(
        @Value("${open.api.rentalHouse.url}") String url,
        @Value("${open.api.rentalHouse.key}") String key) {
        return new RentalHouseApiItemReader(url, key, restTemplate, objectMapper, RentalHouseEnums.SignguCode.values());
    }

    @Bean
    @StepScope
    public ItemProcessor<List<RentalHouseApiResponse>, List<RentalHouse>> processor() {
        return new RentalHouseApiItemProcessor(kakaoGeocodingService);
    }

    @Bean
    public ItemWriter<List<RentalHouse>> writer() {
        return new RentalHouseApiItemWriter(rentalHouseRepository);
    }
}

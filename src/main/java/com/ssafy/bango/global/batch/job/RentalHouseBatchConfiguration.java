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
import com.ssafy.bango.global.batch.tasklet.RentalHouseDeleteDataTasklet;
import com.ssafy.bango.global.batch.tasklet.RentalHouseSummaryGenerateTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
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

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RentalHouseBatchConfiguration {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final CustomStepExecutionListener customStepExecutionListener;

    private final RentalHouseDeleteDataTasklet rentalHouseDeleteDataTasklet;
    private final RentalHouseSummaryGenerateTasklet rentalHouseSummaryGenerateTasklet;

    private final KakaoGeocodingService kakaoGeocodingService;
    private final RentalHouseRepository rentalHouseRepository;

    @Bean(name = "rentalHouseJob")
    public Job rentalHouseJob(Step deleteRentalHouseStep, Step openRentalHouseApiStep, Step summaryStep) {
        return new JobBuilder("rentalHouseJob", jobRepository)
                .start(deleteRentalHouseStep)
                .next(openRentalHouseApiStep)
                .next(summaryStep)
                .build();
    }

    @Bean(name = "deleteRentalHouseStep")
    public Step deleteRentalHouseStep() {
        return new StepBuilder("deleteRentalHouseStep", jobRepository)
                .tasklet(rentalHouseDeleteDataTasklet, transactionManager)
                .build();
    }

    @Bean(name = "summaryStep")
    public Step summaryStep() {
        return new StepBuilder("summaryStep", jobRepository)
            .tasklet(rentalHouseSummaryGenerateTasklet, transactionManager)
            .build();
    }

    @Bean(name = "openRentalHouseApiStep")
    public Step openRentalHouseApiStep(
            ItemReader<List<RentalHouseApiResponse>> openApiItemReader,
            ItemProcessor<List<RentalHouseApiResponse>, List<RentalHouse>> openApiItemProcessor,
            ItemWriter<List<RentalHouse>> openApiItemWriter
    ) {
        return new StepBuilder("openRentalHouseApiStep", jobRepository)
                .<List<RentalHouseApiResponse>, List<RentalHouse>>chunk(1, transactionManager)
                .reader(openApiItemReader)
                .processor(openApiItemProcessor)
                .writer(openApiItemWriter)
                .listener(customStepExecutionListener)
                .build();
    }

    @Bean(name = "rentalHouseOpenApiItemReader")
    @StepScope
    public ItemReader<List<RentalHouseApiResponse>> openApiItemReader(
            @Value("${open.api.rentalHouse.url}") String openApiUrl,
            @Value("${open.api.rentalHouse.key}") String openApiServiceKey) {

        return new RentalHouseApiItemReader(
                openApiUrl,
                openApiServiceKey,
                restTemplate,
                objectMapper,
                RentalHouseEnums.SignguCode.values()
        );
    }

    @Bean(name = "rentalHouseOpenApiItemProcessor")
    @StepScope
    public ItemProcessor<List<RentalHouseApiResponse>, List<RentalHouse>> openApiItemProcessor() {
        return new RentalHouseApiItemProcessor(kakaoGeocodingService);
    }

    @Bean(name = "rentalHouseOpenApiItemWriter")
    public ItemWriter<List<RentalHouse>> openApiItemWriter() {
        return new RentalHouseApiItemWriter(rentalHouseRepository);
    }
}

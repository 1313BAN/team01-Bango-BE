package com.ssafy.bango.global.batch.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.entity.enums.RentalHouseEnums;
import com.ssafy.bango.domain.rentalhouse.repository.RentalHouseRepository;
import com.ssafy.bango.domain.rentalhouse.service.KakaoGeocodingService;
import com.ssafy.bango.global.batch.chunk.OpenApiItemProcessor;
import com.ssafy.bango.global.batch.chunk.OpenApiItemReader;
import com.ssafy.bango.global.batch.chunk.OpenApiItemWriter;
import com.ssafy.bango.global.batch.dto.RentalHouseApiResponse;
import com.ssafy.bango.global.batch.tasklet.DeleteDataTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
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
@EnableBatchProcessing
public class BatchConfiguration {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CustomStepExecutionListener customStepExecutionListener;

    private final DeleteDataTasklet deleteDataTasklet;
    private final KakaoGeocodingService kakaoGeocodingService;
    private final RentalHouseRepository rentalHouseRepository;

    @Bean
    public Job openApiJob(
            Step deleteDataStep,
            Step openApiStep
    ) {
        return new JobBuilder("openApiJob", jobRepository)
                .start(deleteDataStep)
                .next(openApiStep)
                .build();
    }

    @Bean
    public Step deleteDataStep() {
        return new StepBuilder("deleteDataStep", jobRepository)
                .tasklet(deleteDataTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step openApiStep(
            ItemReader<List<RentalHouseApiResponse>> openApiItemReader,
            ItemProcessor<List<RentalHouseApiResponse>, List<RentalHouse>> openApiItemProcessor,
            ItemWriter<List<RentalHouse>> openApiItemWriter
    ) {
        return new StepBuilder("openApiStep", jobRepository)
                .<List<RentalHouseApiResponse>, List<RentalHouse>>chunk(1, transactionManager)
                .reader(openApiItemReader)
                .processor(openApiItemProcessor)
                .writer(openApiItemWriter)
                .listener(customStepExecutionListener)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<List<RentalHouseApiResponse>> openApiItemReader(
            @Value("${open.api.rentalHouse.url}") String openApiUrl,
            @Value("${open.api.rentalHouse.key}") String openApiServiceKey) {

        return new OpenApiItemReader(
                openApiUrl,
                openApiServiceKey,
                new RestTemplate(),
                new ObjectMapper(),
                RentalHouseEnums.SignguCode.values()
        );
    }

    @Bean
    @StepScope
    public ItemProcessor<List<RentalHouseApiResponse>, List<RentalHouse>> openApiItemProcessor() {
        return new OpenApiItemProcessor(kakaoGeocodingService);
    }

    @Bean
    public ItemWriter<List<RentalHouse>> openApiItemWriter() {
        return new OpenApiItemWriter(rentalHouseRepository);
    }
}

package com.ssafy.bango.global.batch.job.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.bango.domain.rentalhouse.entity.Facility;
import com.ssafy.bango.domain.rentalhouse.entity.RentalHouse;
import com.ssafy.bango.domain.rentalhouse.repository.FacilityRepository;
import com.ssafy.bango.domain.rentalhouse.repository.RentalHouseRepository;
import com.ssafy.bango.global.batch.chunk.facility.v1.FacilityApiItemProcessor;
import com.ssafy.bango.global.batch.chunk.facility.v1.FacilityApiItemReader;
import com.ssafy.bango.global.batch.chunk.facility.v1.FacilityApiItemWriter;
import com.ssafy.bango.global.batch.job.listener.CustomStepExecutionListener;
import com.ssafy.bango.global.batch.tasklet.v1.FacilityDeleteDataTasklet;
import java.util.List;
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

@Configuration
@RequiredArgsConstructor
public class FacilityBatchConfiguration {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CustomStepExecutionListener customStepExecutionListener;

    private final RentalHouseRepository rentalHouseRepository;
    private final FacilityDeleteDataTasklet facilityDeleteDataTasklet;
    private final FacilityRepository facilityRepository;

    @Bean(name = "facilityJob")
    public Job facilityJob(Step deleteFacilityStep, Step openFacilityApiStep) {
        return new JobBuilder("facilityJob", jobRepository)
                .start(deleteFacilityStep)
                .next(openFacilityApiStep)
                .build();
    }

    @Bean(name = "deleteFacilityStep")
    public Step deleteFacilityDataStep() {
        return new StepBuilder("deleteFacilityDataStep", jobRepository)
                .tasklet(facilityDeleteDataTasklet, transactionManager)
                .build();
    }

    @Bean(name = "openFacilityApiStep")
    public Step openFacilityApiStep(
        ItemReader<RentalHouse> openApiItemReader,
        ItemProcessor<RentalHouse, List<Facility>> openApiItemProcessor,
        ItemWriter<List<Facility>> openApiItemWriter
    ) {
        return new StepBuilder("openFacilityApiStep", jobRepository)
            .<RentalHouse, List<Facility>>chunk(10, transactionManager)
            .reader(openApiItemReader)
            .processor(openApiItemProcessor)
            .writer(openApiItemWriter)
            .listener(customStepExecutionListener)
            .build();
    }

    @Bean(name = "facilityOpenApiItemReader")
    @StepScope
    public ItemReader<RentalHouse> openApiItemReader() {
        return new FacilityApiItemReader(rentalHouseRepository);
    }

    @Bean(name = "facilityOpenApiItemProcessor")
    @StepScope
    public ItemProcessor<RentalHouse, List<Facility>> openApiItemProcessor(
        @Value("${open.api.facility.url}") String openApiUrl,
        @Value("${open.api.facility.key}") String openApiServiceKey) {
        return new FacilityApiItemProcessor(
            restTemplate,
            objectMapper,
            openApiUrl,
            openApiServiceKey
        );
    }

    @Bean(name = "facilityOpenApiItemWriter")
    public ItemWriter<List<Facility>> openApiItemWriter() {
        return new FacilityApiItemWriter(facilityRepository);
    }
}

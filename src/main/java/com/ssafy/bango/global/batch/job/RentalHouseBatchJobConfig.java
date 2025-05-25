package com.ssafy.bango.global.batch.job;

import com.ssafy.bango.global.batch.tasklet.RentalHouseDeleteDataTasklet;
import com.ssafy.bango.global.batch.tasklet.RentalHouseSummaryGenerateTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class RentalHouseBatchJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final RentalHouseDeleteDataTasklet deleteTasklet;
    private final RentalHouseSummaryGenerateTasklet summaryTasklet;

    @Bean
    public Job rentalHouseJob(Step deleteStep, Step fetchStep, Step summaryStep) {
        return new JobBuilder("rentalHouseJob", jobRepository)
                .start(deleteStep)
                .next(fetchStep)
                .next(summaryStep)
                .build();
    }

    @Bean
    public Step deleteStep() {
        return new StepBuilder("deleteStep", jobRepository)
                .tasklet(deleteTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step summaryStep() {
        return new StepBuilder("summaryStep", jobRepository)
            .tasklet(summaryTasklet, transactionManager)
            .build();
    }
}

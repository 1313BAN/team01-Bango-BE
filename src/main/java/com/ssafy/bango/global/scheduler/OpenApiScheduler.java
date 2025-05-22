package com.ssafy.bango.global.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenApiScheduler {
    @Qualifier("rentalHouseJob")
    private final Job rentalHouseJob;

    @Qualifier("facilityJob")
    private final Job facilityJob;

    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0 0 4 * * ?")
    public void runRentalHouseBatch() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("run.id", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(rentalHouseJob, params);
            log.info("[OpenApiScheduler] 임대 주택 배치 실행 완료");
        } catch (Exception e) {
            log.error("[OpenApiScheduler] 임대 주택 배치 실행 실패", e);
        }
    }

    @Scheduled(cron = "0 30 4 * * ?")
    public void runFacilityBatch() {
        try {
            JobParameters params = new JobParametersBuilder()
                .addLong("run.id", System.currentTimeMillis())
                .toJobParameters();

            jobLauncher.run(facilityJob, params);
            log.info("[OpenApiScheduler] 시설 정보 배치 실행 완료");
        } catch (Exception e) {
            log.error("[OpenApiScheduler] 시설 정보 배치 실행 실패", e);
        }
    }
}

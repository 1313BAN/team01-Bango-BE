package com.ssafy.bango.global.common.health;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;

@Slf4j
@RestController
@RequiredArgsConstructor
//@Profile("local")
public class BatchController {
    @Qualifier("rentalHouseJob")
    private final Job rentalHouseJob;

    @Qualifier("facilityJob")
    private final Job facilityJob;

    private final JobLauncher jobLauncher;

    @GetMapping("/batch-rentalhouse-job")
    public String runRentalHouseBatch() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("run.id", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(rentalHouseJob, params);
            log.info("[OpenApiScheduler] 공공 API 배치 실행 완료");
            return "[OpenApiScheduler] 공공 API 배치 실행 완료";
        } catch (Exception e) {
            log.error("[OpenApiScheduler] 공공 API 배치 실행 실패", e);
            return "[OpenApiScheduler] 공공 API 배치 실행 실패";
        }
    }

    @GetMapping("/batch-facility-job")
    public String runFacilityBatch() {
        try {
            JobParameters params = new JobParametersBuilder()
                .addLong("run.id", System.currentTimeMillis())
                .toJobParameters();

            jobLauncher.run(facilityJob, params);
            log.info("[OpenApiScheduler] 인프라 API 배치 실행 완료");
            return "[OpenApiScheduler] 인프라 API 배치 실행 완료";
        } catch (Exception e) {
            log.error("[OpenApiScheduler] 인프라 API 배치 실행 실패", e);
            return "[OpenApiScheduler] 인프라 API 배치 실행 실패";
        }
    }
}

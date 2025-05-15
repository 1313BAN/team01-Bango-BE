package com.ssafy.bango.global.common.health;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BatchController {
    private final Job batchJob;
    private final JobLauncher jobLauncher;

    @GetMapping("/batch-job")
    public String runBatch() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("run.id", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(batchJob, params);
            log.info("[OpenApiScheduler] 공공 API 배치 실행 완료");
            return "[OpenApiScheduler] 공공 API 배치 실행 완료";
        } catch (Exception e) {
            log.error("[OpenApiScheduler] 공공 API 배치 실행 실패", e);
            return "[OpenApiScheduler] 공공 API 배치 실행 실패";
        }
    }
}

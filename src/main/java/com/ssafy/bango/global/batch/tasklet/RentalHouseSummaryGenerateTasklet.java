package com.ssafy.bango.global.batch.tasklet;

import com.ssafy.bango.domain.rentalhouse.service.RentalHouseSummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RentalHouseSummaryGenerateTasklet implements Tasklet {
    private final RentalHouseSummaryService rentalHouseSummaryService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        try {
            log.info("임대주택 요약 데이터 생성 작업 시작");
            rentalHouseSummaryService.makeAndSaveSummary();
            log.info("임대주택 요약 데이터 생성 작업 완료");
        } catch (Exception e) {
            log.error("임대주택 요약 데이터 생성 중 오류 발생", e);
            throw e;
        }
        return RepeatStatus.FINISHED;
    }
}

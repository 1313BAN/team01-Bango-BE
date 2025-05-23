package com.ssafy.bango.global.batch.tasklet;

import com.ssafy.bango.domain.rentalhouse.repository.RentalHouseRepository;
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
public class RentalHouseDeleteDataTasklet implements Tasklet {
    private final RentalHouseRepository rentalHouseRepository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        try {
            log.info("임대주택 데이터({} 건) 삭제 작업 시작", rentalHouseRepository.count());
            rentalHouseRepository.deleteAll();
            log.info("임대주택 데이터 삭제 작업 완료");
        } catch (Exception e) {
            log.error("임대주택 데이터 삭제 중 오류 발생", e);
            throw e;
        }
        return RepeatStatus.FINISHED;
    }
}
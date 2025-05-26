package com.ssafy.bango.global.batch.tasklet.v1;

import com.ssafy.bango.domain.rentalhouse.repository.FacilityRepository;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
public class FacilityDeleteDataTasklet implements Tasklet {
    private final FacilityRepository facilityRepository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        try {
            log.info("시설 데이터({} 건) 삭제 작업 시작", facilityRepository.count());
            facilityRepository.deleteAll();
            log.info("시설 데이터 삭제 작업 완료");
        } catch (Exception e) {
            log.error("시설 데이터 삭제 중 오류 발생", e);
            throw e;
        }
        return RepeatStatus.FINISHED;
    }
}
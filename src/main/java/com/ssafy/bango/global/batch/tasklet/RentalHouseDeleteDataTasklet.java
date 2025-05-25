package com.ssafy.bango.global.batch.tasklet;

import com.ssafy.bango.domain.rentalhouse.repository.RentalHouseRepository;
import com.ssafy.bango.domain.rentalhouse.repository.RentalHouseStyleRepository;
import com.ssafy.bango.domain.rentalhouse.repository.RentalHouseSummaryRepository;
import jakarta.transaction.Transactional;
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
public class RentalHouseDeleteDataTasklet implements Tasklet {
    private final RentalHouseRepository rentalHouseRepository;
    private final RentalHouseStyleRepository rentalHouseStyleRepository;
    private final RentalHouseSummaryRepository rentalHouseSummaryRepository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        try {
            long summaryCount = rentalHouseSummaryRepository.count();
            long rentalCount = rentalHouseRepository.count();

            log.info("임대주택 요약 데이터({} 건) 삭제 작업 시작", summaryCount);
            rentalHouseSummaryRepository.deleteAll();
            log.info("임대주택 요약 데이터 삭제 작업 완료");

            log.info("임대주택 데이터({} 건) 삭제 작업 시작", rentalCount);
            rentalHouseStyleRepository.deleteAll();
            rentalHouseRepository.deleteAll();
            log.info("임대주택 데이터 삭제 작업 완료");
        } catch (Exception e) {
            log.error("임대주택 데이터 삭제 중 오류 발생", e);
            throw e;
        }
        return RepeatStatus.FINISHED;
    }
}
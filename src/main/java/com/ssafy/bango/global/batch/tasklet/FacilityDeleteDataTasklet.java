package com.ssafy.bango.global.batch.tasklet;

import com.ssafy.bango.domain.rentalhouse.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FacilityDeleteDataTasklet implements Tasklet {
    private final FacilityRepository facilityRepository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        facilityRepository.deleteAll();
        return RepeatStatus.FINISHED;
    }
}
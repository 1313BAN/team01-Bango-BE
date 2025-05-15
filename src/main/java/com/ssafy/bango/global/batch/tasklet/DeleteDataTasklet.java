package com.ssafy.bango.global.batch.tasklet;

import com.ssafy.bango.domain.rentalhouse.service.RentalHouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteDataTasklet implements Tasklet {
    private final RentalHouseService rentalHouseService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        rentalHouseService.deleteAll();
        return RepeatStatus.FINISHED;
    }
}
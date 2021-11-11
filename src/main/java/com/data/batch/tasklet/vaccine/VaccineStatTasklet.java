package com.data.batch.tasklet.vaccine;

import com.data.batch.service.vaccine.VaccineStatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class VaccineStatTasklet implements Tasklet {

    private static final Logger logger = LoggerFactory.getLogger(VaccineStatTasklet.class);

    @Autowired
    private VaccineStatService vaccineStatService;

    public VaccineStatTasklet() {logger.info(">>>>>> VaccineStatTasklet 생성");}

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)  throws Exception {
        vaccineStatService.vaccineStat();
        return RepeatStatus.FINISHED;
    }
}

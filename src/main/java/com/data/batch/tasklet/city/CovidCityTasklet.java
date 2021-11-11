package com.data.batch.tasklet.city;


import com.data.batch.service.CovidCityService;
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
public class CovidCityTasklet implements Tasklet {

    private static final Logger logger = LoggerFactory.getLogger(CovidCityTasklet.class);

    @Autowired
    private CovidCityService covidCityService;

    public CovidCityTasklet() {
        logger.info(">> CovidCityTaskLet 생성");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        covidCityService.covidCity();
        return RepeatStatus.FINISHED;
    }
}

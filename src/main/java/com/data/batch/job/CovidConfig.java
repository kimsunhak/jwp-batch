package com.data.batch.job;

import com.data.batch.tasklet.center.CovidCenterTasklet;
import com.data.batch.tasklet.city.CovidCityTasklet;
import com.data.batch.tasklet.vaccine.VaccineStatTasklet;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CovidConfig {

    private static final Logger logger = LoggerFactory.getLogger(CovidConfig.class);

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final CovidCityTasklet covidCityTasklet;

    private final CovidCenterTasklet covidCenterTasklet;

    private final VaccineStatTasklet vaccineStatTasklet;


    @Bean
    public Job covidJob() {
        logger.info(">>>>>> Definition CovidJob");
        return jobBuilderFactory.get("covidJob")
                .start(covidCityStep())
                .next(vaccineStatStep())
                .next(covidCenterStep())
                .build();
    }

    @Bean
    public Step covidCityStep() {
        logger.info(">>>>>> Definition CovidCityStep");
        return stepBuilderFactory.get("covidCityStep")
                .tasklet(covidCityTasklet)
                .build();
    }

    @Bean
    public Step covidCenterStep() {
        logger.info(">>>>>> Definition CovidCenterStep");
        return stepBuilderFactory.get("covidCenterStep")
                .tasklet(covidCenterTasklet)
                .build();
    }

    @Bean
    public Step vaccineStatStep() {
        logger.info(">>>>>> Definition VaccineStatStep");
        return stepBuilderFactory.get("vaccineStatStep")
                .tasklet(vaccineStatTasklet)
                .build();
    }

}

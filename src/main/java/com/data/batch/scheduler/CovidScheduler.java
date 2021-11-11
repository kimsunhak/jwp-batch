package com.data.batch.scheduler;

import com.data.batch.job.CovidConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CovidScheduler {

    private static final Logger logger = LoggerFactory.getLogger(CovidScheduler.class);

    private final JobLauncher jobLauncher;

    private final CovidConfig covidConfig;

    @Scheduled(cron = "0 0 13 * * *") // 실제 배포용
//    @Scheduled(initialDelay = 10000, fixedDelay = 30000) // 테스트용
    public void runCovidJob() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());

        Map<String, JobParameter> param = new HashMap<>();
        param.put("JobParamTime", new JobParameter(date));
        JobParameters jobParameters = new JobParameters(param);
        try {
            jobLauncher.run(covidConfig.covidJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobParametersInvalidException | JobInstanceAlreadyCompleteException ex) {
            logger.error(ex.getMessage());
        }
    }
}

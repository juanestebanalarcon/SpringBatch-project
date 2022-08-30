package com.jeam.springbatch.springbatchfirstproject.service;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JobService {

    @Autowired
    private JobLauncher jobLauncher;
    @Qualifier("firstJob")
    @Autowired
    Job firstJob;
    @Qualifier("secondJob")
    @Autowired
    Job secondJob;
    @Async
public void startJob(String jobName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime",new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(params);
        try{
            JobExecution jobExecution=null;
        if(jobName.equals("First_Job")){
           jobExecution = jobLauncher.run(firstJob,jobParameters);
        }
       jobExecution = jobLauncher.run(secondJob,jobParameters);
        System.out.println("JobExecution ID = "+jobExecution.getId());
        }
        catch (Exception e){
            System.out.println("Exception while starting job: "+e);

        }
}

}

package com.jeam.springbatch.springbatchfirstproject.config;

import  com.jeam.springbatch.springbatchfirstproject.listener.FirstJobListener;
import  com.jeam.springbatch.springbatchfirstproject.listener.firstStepListener;
import com.jeam.springbatch.springbatchfirstproject.service.FirstTasklet;
import com.jeam.springbatch.springbatchfirstproject.service.SecondTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleJob {

    @Autowired
    private SecondTasklet secondTask;
    @Autowired
    private FirstTasklet firstTask;
    @Autowired
   private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    FirstJobListener firstJobListener;

    @Autowired
    firstStepListener FirstStepListener;

  //  @Bean
    public Job firstJob(){
      return jobBuilderFactory.get("First_Job")
              .incrementer(new RunIdIncrementer())
              .start(firstStep())
              .next(secondStep())
              .listener(firstJobListener)
              .build();
    }
    private Step firstStep(){
     return   stepBuilderFactory.get("First_step")
                .listener(FirstStepListener)
                .tasklet(firstTask).build();
    }
//    private Tasklet firstTask(){
//        return new Tasklet() {
//            @Override
//            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
//                System.out.println("first tasklet step");
//                return RepeatStatus.FINISHED;
//            }
//        };
//    }
    private Step secondStep(){
        return   stepBuilderFactory.get("Second_step")
                .tasklet(secondTask).build();
    }
//    private Tasklet secondTask(){
//        return new Tasklet() {
//            @Override
//            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
//                System.out.println("second tasklet step");
//                return RepeatStatus.FINISHED;
//            }
//        };
//    }
    //Seconde jobs
@Bean
public Job secondJob(){
        return jobBuilderFactory.get("Second job").incrementer(new RunIdIncrementer())
                .build();
}

}

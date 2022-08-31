package com.jeam.springbatch.springbatchfirstproject.config;

import  com.jeam.springbatch.springbatchfirstproject.listener.FirstJobListener;
import  com.jeam.springbatch.springbatchfirstproject.listener.firstStepListener;
import com.jeam.springbatch.springbatchfirstproject.model.StudentCsv;
import com.jeam.springbatch.springbatchfirstproject.model.StudentJson;
import com.jeam.springbatch.springbatchfirstproject.processor.FirstItemProcessor;
import com.jeam.springbatch.springbatchfirstproject.reader.FirstItemReader;
import com.jeam.springbatch.springbatchfirstproject.service.FirstTasklet;
import com.jeam.springbatch.springbatchfirstproject.service.SecondTasklet;
import com.jeam.springbatch.springbatchfirstproject.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class SampleJob {

    @Autowired
    private FirstItemReader firstItemReader;
    @Autowired
    private FirstItemProcessor firstItemProcessor;
    @Autowired
    private FirstItemWriter firstItemWriter;
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

    @Bean
    public Job firstJob() {
        return jobBuilderFactory.get("First_Job")
                .incrementer(new RunIdIncrementer())
                .start(firstStep())
                .next(secondStep())
                .listener(firstJobListener)
                .build();
    }

    private Step firstStep() {
        return stepBuilderFactory.get("First_step")
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
    private Step secondStep() {
        return stepBuilderFactory.get("Second_step")
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
    public Job secondJob() {
        return jobBuilderFactory.get("Second job").incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .next(secondStep())
                .build();
    }

    private Step firstChunkStep() {
        return stepBuilderFactory.get("first junk step")
                //3 registros
                .<StudentJson, StudentJson>chunk(3)
        //        .reader(flatFileItemReader(null))
                .reader(jsonItemReader(null))
                //        .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public FlatFileItemReader flatFileItemReader(@Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource) {
        FlatFileItemReader flatFileItemReader = new FlatFileItemReader<StudentCsv>();
        DefaultLineMapper<StudentCsv> defaultLineMapper = new DefaultLineMapper<StudentCsv>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("ID", "First Name", "Last Name", "Email");
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        BeanWrapperFieldSetMapper<StudentCsv> fieldSetMapper = new BeanWrapperFieldSetMapper<StudentCsv>();
        fieldSetMapper.setTargetType(StudentCsv.class);

//        flatFileItemReader
//                .setResource(fileSystemResource);
//        flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>(){
//            {
//                setLineTokenizer(new DelimitedLineTokenizer() {
////                setLineTokenizer(new DelimitedLineTokenizer("delimiter")
//                    {
//                    setNames("ID","First Name","Last Name","Email");
////                    setDelimiter("|");
//                    }
//                });
//                setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>(){
//                    {
//                        setTargetType(StudentCsv.class);
//                    }
//                });
//            }
//        });
        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }
    @StepScope
    @Bean
    public JsonItemReader<StudentJson> jsonItemReader(@Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource){
        JsonItemReader<StudentJson>jsonJsonItemReader=new JsonItemReader<StudentJson>();
        jsonJsonItemReader.setResource(fileSystemResource);
        jsonJsonItemReader.setJsonObjectReader(
                new JacksonJsonObjectReader<>(StudentJson.class)
        );
        return jsonJsonItemReader;
    }

}



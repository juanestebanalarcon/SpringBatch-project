package com.jeam.springbatch.springbatchfirstproject.config;

import  com.jeam.springbatch.springbatchfirstproject.listener.FirstJobListener;
import  com.jeam.springbatch.springbatchfirstproject.listener.firstStepListener;
import com.jeam.springbatch.springbatchfirstproject.model.*;
import com.jeam.springbatch.springbatchfirstproject.processor.FirstItemProcessor;
import com.jeam.springbatch.springbatchfirstproject.reader.FirstItemReader;
import com.jeam.springbatch.springbatchfirstproject.service.FirstTasklet;
import com.jeam.springbatch.springbatchfirstproject.service.SecondTasklet;
import com.jeam.springbatch.springbatchfirstproject.service.StudentService;
import com.jeam.springbatch.springbatchfirstproject.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

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

//   @Autowired
    private DataSource dataSource;

    /*
    Multiple datasources:
    @Bean
    @ConfigurationProperties(prefix="spring.datasourcename")
    public DataSource datasource(){
        return DataSourceBuilder.create().build();
    }
    */

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
                .<StudentJDBC, StudentJDBC>chunk(3)
        //        .reader(flatFileItemReader(null))
//                .reader(jsonItemReader(null))
//                .reader(staxEventItemReader(null))
                .reader(JDBCJdbcCursorItemReader())
              //  .reader(itemReaderAdapterREST())
//                .processor(firstItemProcessor)
             //   .writer(firstItemWriter)
//                .skip(archiveToSkip.class)
//                .retryLimit(1)
//                .retry(Throwable.class)
                .writer(jsonFileItemWriter(null))
//                .writer(flatFileItemWriter_(null))
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
    @StepScope
    @Bean
    public StaxEventItemReader<StudentXml>staxEventItemReader(
            @Value("#{jobParameters['inputFile']}")
                    FileSystemResource fileSystemResource){
        StaxEventItemReader<StudentXml>staxEventItemReader =
                new StaxEventItemReader<StudentXml>();
        staxEventItemReader.setResource(fileSystemResource);
        staxEventItemReader.setFragmentRootElementName("student");
        staxEventItemReader.setUnmarshaller(new Jaxb2Marshaller(){
            {
                setClassesToBeBound(StudentXml.class);
            }
        });
        return staxEventItemReader;
    }
    public JdbcCursorItemReader<StudentJDBC>JDBCJdbcCursorItemReader(){
        JdbcCursorItemReader<StudentJDBC>jdbcJdbcCursorItemReader=new JdbcCursorItemReader<StudentJDBC>();
        jdbcJdbcCursorItemReader.setDataSource(dataSource);
        jdbcJdbcCursorItemReader.setSql("SELECT * FROM student");
        jdbcJdbcCursorItemReader.setRowMapper(new BeanPropertyRowMapper<>(){
            {
                setMappedClass(StudentJDBC.class);

            }
        });
        return jdbcJdbcCursorItemReader;
    }
    public ItemReaderAdapter<StudentResponse>itemReaderAdapterREST(){
        ItemReaderAdapter itemReaderAdapter = new ItemReaderAdapter<StudentResponse>();
        itemReaderAdapter.setTargetObject(StudentService.class);
        itemReaderAdapter.setTargetMethod("getStudent");
        return itemReaderAdapter;

    }
    @StepScope
    @Bean
    public FlatFileItemWriter<StudentService>flatFileItemWriter_(
            @Value("#{jobParameters['outputFile']}")FileSystemResource fileSystemResource){
        FlatFileItemWriter<StudentService>flatFileItemWriter = new FlatFileItemWriter<>();
        flatFileItemWriter.setHeaderCallback(new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                writer.write("Id, First Name, Last Name, Email");
            }
        });
        flatFileItemWriter.setLineAggregator(new DelimitedLineAggregator<>(){
            {
                setFieldExtractor(new BeanWrapperFieldExtractor<StudentJDBC>(){
                    {
                        setNames(new String[] {"id","firstName","lastName","email"});
                    }
                });

            }
        });
        flatFileItemWriter.setFooterCallback(new FlatFileFooterCallback() {
            @Override
            public void writeFooter(Writer writer) throws IOException {
                writer.write("Created @ "+ new Date());
            }
        });
        flatFileItemWriter.setResource(fileSystemResource);
        return flatFileItemWriter;
    }
    public JsonFileItemWriter<StudentJDBC>jsonFileItemWriter(
            @Value("#{jobParameters['inputFile']}")FileSystemResource fileSystemResource
    ){
        JsonFileItemWriter<StudentJDBC> jsonFileItemWriter =
                new JsonFileItemWriter<>(fileSystemResource,new JacksonJsonObjectMarshaller<StudentJDBC>());
   return jsonFileItemWriter;
    }
}



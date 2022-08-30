package com.jeam.springbatch.springbatchfirstproject.writer;

import com.jeam.springbatch.springbatchfirstproject.model.StudentCsv;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirstItemWriter implements ItemWriter<StudentCsv> {

    @Override
    public void write(List<? extends StudentCsv> items) throws Exception {
        System.out.println("Inside item writer.");
        items.stream().forEach(System.out::println);

    }
}

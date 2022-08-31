package com.jeam.springbatch.springbatchfirstproject.writer;

import com.jeam.springbatch.springbatchfirstproject.model.*;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirstItemWriter implements ItemWriter<StudentResponse> {

    @Override
    public void write(List<? extends StudentResponse> items) throws Exception {
        System.out.println("Inside item writer.");
        items.stream().forEach(System.out::println);

    }
}

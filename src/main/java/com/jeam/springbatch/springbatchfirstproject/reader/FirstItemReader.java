package com.jeam.springbatch.springbatchfirstproject.reader;

import org.springframework.stereotype.Component;

import javax.batch.api.chunk.ItemReader;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Component
public class FirstItemReader implements ItemReader<Integer> {
    List<Integer>list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

    @Override
    public void open(Serializable serializable) throws Exception {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public Object readItem() throws Exception {
        return null;
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }
}

package com.jeam.springbatch.springbatchfirstproject.listener;

import com.jeam.springbatch.springbatchfirstproject.model.StudentCsv;
import com.jeam.springbatch.springbatchfirstproject.model.StudentJson;
import org.springframework.stereotype.Component;
import org.springframework.batch.core.SkipListener;
@Component
public class SkipListener implements org.springframework.batch.core.SkipListener<StudentCsv,StudentJson> {


    @Override
    public void onSkipInRead(Throwable throwable) {

    }

    @Override
    public void onSkipInWrite(StudentJson studentJson, Throwable throwable) {

    }

    @Override
    public void onSkipInProcess(StudentCsv studentCsv, Throwable throwable) {

    }
}

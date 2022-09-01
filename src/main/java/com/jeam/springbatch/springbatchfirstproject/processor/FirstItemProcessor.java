package com.jeam.springbatch.springbatchfirstproject.processor;

import com.jeam.springbatch.springbatchfirstproject.model.StudentJDBC;
import com.jeam.springbatch.springbatchfirstproject.model.StudentJson;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class FirstItemProcessor implements ItemProcessor<StudentJDBC, StudentJson> {

    @Override
    public StudentJson process(StudentJDBC item) throws Exception {
        System.out.println("Inside Item processor.");
        StudentJson studentJson = new StudentJson();
        studentJson.setID(item.getID());
        studentJson.setFirstName(item.getFirstName());
        studentJson.setLastName(item.getLastName());
        studentJson.setEmail(item.getEmail());

        return studentJson;
    }
}

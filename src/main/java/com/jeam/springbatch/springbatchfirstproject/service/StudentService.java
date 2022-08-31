package com.jeam.springbatch.springbatchfirstproject.service;

import com.jeam.springbatch.springbatchfirstproject.model.StudentJDBC;
import com.jeam.springbatch.springbatchfirstproject.model.StudentResponse;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    public List<StudentResponse> restCallToGetStudents(){
        RestTemplate restTemplate = new RestTemplate();
        StudentResponse [] studentJDBCS = restTemplate.getForObject(
                "http:localhost;9000/api/students/all",
                StudentResponse[].class
        );
        List<StudentResponse>list_resp = new ArrayList<>();
        for(StudentResponse sr:studentJDBCS){
            list_resp.add(sr);
        }
        return list_resp;
    }
    public StudentResponse getStudent(){

    }
}

package com.jeam.springbatch.springbatchfirstproject.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name="students")
public class StudentXml {
    private Long ID;
    private String firstName,lastName,email;

    @Override
    public String toString() {
        return "StudentXml{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

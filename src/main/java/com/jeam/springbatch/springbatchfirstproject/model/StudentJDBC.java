package com.jeam.springbatch.springbatchfirstproject.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentJDBC {
    private Long ID;
    private String firstName,lastName,email;

    @Override
    public String toString() {
        return "StudentJDBC{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

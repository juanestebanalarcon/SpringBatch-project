package com.jeam.springbatch.springbatchfirstproject.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentJson {
    private Long ID;
    private String firstName,lastName,email;

    public StudentJson(Long ID, String firstName, String lastName, String email) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "StudentJson{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    public StudentJson(){}
}

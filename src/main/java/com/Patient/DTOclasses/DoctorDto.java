package com.Patient.DTOclasses;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class DoctorDto {
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "specialization cannot be null")
    private String specialization;
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 to 15 digits and can start with '+'")
    private String phoneNumber;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization(){
        return specialization;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setSpecialization(String specialization){
        this.specialization = specialization;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

}

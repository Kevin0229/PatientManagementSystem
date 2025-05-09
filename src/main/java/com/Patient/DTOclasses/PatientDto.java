package com.Patient.DTOclasses;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class PatientDto {
    @NotNull(message = "Name cannot be null")
    private String name;
    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    private String email;
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 to 15 digits and can start with '+'")
    @NotNull(message = "Phone number cannot be null")
    private String phoneNumber;
    
    private Long doctorId; 


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    
   
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}


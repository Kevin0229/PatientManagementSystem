package com.Patient.DTOclasses;

public class PatientDto {
    private String name;
    private String email;
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



package com.Patient.DTOclasses;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class DoctorDto {
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Profession cannot be null")
    private String profession;
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 to 15 digits and can start with '+'")
    private String phoneNumber;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession(){
        return profession;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setProfession(String profession){
        this.profession = profession;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

}

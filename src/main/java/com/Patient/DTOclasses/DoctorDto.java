package com.Patient.DTOclasses;

public class DoctorDto {
    private String name;
    private String profession;
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

package com.Patient.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String emailId;
    private String specialization;
    private String phoneNumber;
    private String Password;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Patient> patients;

    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }

    public String getSpecializaion(){
        return specialization;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }


    public List<Patient> getList(){
        return patients;
    }

    public String getPassword(){
        return Password;
    }

    public String getEmailId(){
        return emailId;
    }

    public void setId(Long id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setList(List<Patient> patients){
        this.patients = patients;
    }

    public void setSpecialization(String specialization){
        this.specialization = specialization;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String Password){
        this.Password = Password;
    }

    public void setEmailId(String emailId){
        this.emailId = emailId;
    }
}
package com.Patient.model;
import java.util.List;

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

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Patient> patients;

    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public List<Patient> getList(){
        return patients;
    }

    public void setPatientId(Long id){
        this.id = id;
    }
    public void setPatientName(String name){
        this.name = name;
    }
    public void setPatientList(List<Patient> patients){
        this.patients = patients;
    }
}

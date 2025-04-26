package com.Patient.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class MedicalRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symptoms;
    private String diagnosis;
    private String prescription;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "Patient_Id")
    @JsonBackReference
    private Patient patient;


    public long getId(){
        return id;
    }

    public String getSymptoms(){
        return symptoms;
    }

    public String getDiagnosis(){
        return diagnosis;
    }

    public String getPrescription(){
        return prescription;
    }

    public LocalDate getDate(){
        return date;
    }

    public Patient getPatient(){
        return patient;
    }



    public void setSymptoms(String symptoms){
        this.symptoms = symptoms;
    }

    public void setDiagnosis(String diagnosis){
        this.diagnosis = diagnosis;
    }

    public void setPrescription(String prescription){
        this.prescription = prescription;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public void setPatient(Patient patient){
        this.patient = patient;
    }
}
package com.Patient.DTOclasses;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class MedicalRecordsDto {
    @NotNull(message = "Symptoms cannot be null")
    private String symptoms;
    @NotNull(message = "Diagnosis cannot be null")  
    private String diagnosis;
    @NotNull(message = "Prescription cannot be null")
    private String prescription;
   
    private LocalDate date;

    private long patientId;

    public String getSymptoms() {
        return symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getPatientId(){
        return patientId;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPatientId(long patient){
        this.patientId = patient;
    }

}

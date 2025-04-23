package com.Patient.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Patient.DTOclasses.DoctorDto;
import com.Patient.DTOclasses.MedicalRecordsDto;
import com.Patient.DTOclasses.PatientDto;
import com.Patient.model.Doctor;
import com.Patient.model.MedicalRecords;
import com.Patient.model.Patient;
import com.Patient.repository.DoctorRepo;
import com.Patient.repository.MedicalRecordsRepo;
import com.Patient.repository.PatientRepo;

@Service
public class PatientService {
    
    @Autowired
    PatientRepo Patientrepo;

    @Autowired
    DoctorRepo Doctorrepo;

    @Autowired
    MedicalRecordsRepo MedicalRecordsrepo;

    public PatientDto newPatientAdd( PatientDto patient){
        Patient newPatient = new Patient();
        newPatient.setName(patient.getName());
        newPatient.setEmail(patient.getEmail());
        newPatient.setPhoneNumber(patient.getPhoneNumber());

        Doctor doctor= Doctorrepo.findById(patient.getDoctorId()).orElseThrow(() -> new RuntimeException("Doctor not found"));
        newPatient.setDoctor(doctor);
        Patient p = Patientrepo.save(newPatient);
        PatientDto pdto = new PatientDto();
        pdto.setName(p.getName());
        pdto.setEmail(p.getEmail());
        pdto.setPhoneNumber(p.getPhoneNumber());
        return pdto;
        
    }

    public Doctor DoctorInclusion(DoctorDto doctor){
        Doctor d = new Doctor();
        d.setName(doctor.getName());
        d.setProfession(doctor.getProfession());
        d.setPhoneNumber(doctor.getPhoneNumber());
    
        return Doctorrepo.save(d);
    }

    public MedicalRecordsDto newRecord(MedicalRecordsDto medrec){
        MedicalRecords med = new MedicalRecords();
        med.setSymptoms(medrec.getSymptoms());
        med.setDiagnosis(medrec.getDiagnosis());
        med.setPrescription(medrec.getPrescription());
        med.setDate(LocalDate.now());
        
        Patient p = Patientrepo.findById(medrec.getPatientId()).orElseThrow(()-> new RuntimeException("Patient not found"));
        med.setPatient(p);
        MedicalRecords medical = MedicalRecordsrepo.save(med);

        MedicalRecordsDto dtom = new MedicalRecordsDto();

        dtom.setDate(medical.getDate());
        dtom.setDiagnosis(medical.getDiagnosis());
        dtom.setPrescription(medical.getPrescription());
        dtom.setSymptoms(medical.getSymptoms());
        return dtom;
    }

    @Transactional
    public List<Patient> totalPatient(Long DoctorId){
        Doctor doc = Doctorrepo.findById(DoctorId).orElseThrow(()->new RuntimeException("NO DOC"));
        return doc.getList();
    }

    public PatientDto singlePatient(long DoctorId,long PatientId){
        Patient p =  Patientrepo.GetPatientDetails(DoctorId,PatientId).orElseThrow(()->new RuntimeException("NO PATIENT"));
        PatientDto pdto = new PatientDto();
        pdto.setName(p.getName());
        pdto.setEmail(p.getEmail());
        pdto.setPhoneNumber(p.getPhoneNumber());

        return pdto;
    }
}

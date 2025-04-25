package com.Patient.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    public List<MedicalRecords> totalMedrecord(long doctorId, long patientId){
        List<MedicalRecords> med = MedicalRecordsrepo.GetMedicalRecords(doctorId,patientId,LocalDate.now());
        return med;
    }

    public List<DoctorDto> totalDoctor(){
        List<Doctor> docs =  Doctorrepo.findAll();

        List<DoctorDto> dto = new ArrayList<>();

        for(Doctor i : docs){
            DoctorDto d = new DoctorDto();
            d.setName(i.getName());
            d.setPhoneNumber(i.getPhoneNumber());
            d.setProfession(i.getProfession());

            dto.add(d);
        }

        return dto;
    }

    public ResponseEntity<String> updateDoctor(long DoctorId,DoctorDto doctorDto){
        Doctor doc = Doctorrepo.findById(DoctorId).orElseThrow(()-> new RuntimeException("Could not find ID "+DoctorId));

        doc.setName(doctorDto.getName());
        doc.setProfession(doctorDto.getProfession());
        doc.setPhoneNumber(doctorDto.getPhoneNumber());

        Doctorrepo.save(doc);
        return ResponseEntity.ok("The Doctor details have been Successfully updated!");
    }

    public ResponseEntity<String> updatePatient(long PatientId,PatientDto patientDto){
        Patient patient = Patientrepo.GetPatientDetails(patientDto.getDoctorId(),PatientId).orElseThrow(()->new RuntimeException("NO PATIENT"));

        patient.setEmail(patientDto.getEmail());
        patient.setPhoneNumber(patientDto.getPhoneNumber());

        Patientrepo.save(patient);

        return ResponseEntity.ok("The Patient of ID "+PatientId+" has been successfully updated!");

    }

    public ResponseEntity<String> DeletePatient(long DoctorId,long PatientId){
        Patient patient = Patientrepo.GetPatientDetails(DoctorId,PatientId).orElseThrow(()->new RuntimeException("NO PATIENT"));
        List<MedicalRecords> medrec = patient.getMedicalRecords();

        for(MedicalRecords i : medrec){
            MedicalRecordsrepo.deleteById(i.getId());
        }

        Patientrepo.deleteById(PatientId);

        return ResponseEntity.ok("The Patient of ID "+PatientId+" Has been deleted along with all their records!");
    }

    public ResponseEntity<String> DeleteDoctor(long DoctorId, long NewDoctorId){
        Doctor doc = Doctorrepo.findById(DoctorId).orElseThrow(()-> new RuntimeException("Could not find ID "+DoctorId));
        List<Patient> p = doc.getList();
        Doctor D = Doctorrepo.findById(NewDoctorId).orElseThrow(()-> new RuntimeException("Could not find ID "+DoctorId));
        for(Patient i : p){
            i.setDoctor(D);
            Patientrepo.save(i);
        }

        Doctorrepo.delete(doc);

        return ResponseEntity.ok("The Doctor of ID "+DoctorId+" Has been deleted and Doctor with ID "+NewDoctorId+" has been replaced successfully!");
    }
}

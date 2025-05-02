package com.Patient.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Patient.DTOclasses.DoctorDto;
import com.Patient.DTOclasses.MedicalRecordsDto;
import com.Patient.DTOclasses.PatientDto;
import com.Patient.Exception.DoctorNotFoundException;
import com.Patient.Exception.MedicalRecordNotFoundException;
import com.Patient.Exception.PatientNotFoundException;
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

    @Autowired
    PasswordEncoder passwordEncoder;
    
   

    public PatientDto newPatientAdd( PatientDto patient){
        Patient newPatient = new Patient();
        newPatient.setName(patient.getName());
        newPatient.setEmail(patient.getEmail());
        newPatient.setPhoneNumber(patient.getPhoneNumber());

        Doctor doctor= Doctorrepo.findById(patient.getDoctorId()).orElseThrow(() -> new DoctorNotFoundException("Doctor not found!"));
        newPatient.setDoctor(doctor);
        Patient p = Patientrepo.save(newPatient);
        PatientDto pdto = new PatientDto();
        pdto.setName(p.getName());
        pdto.setEmail(p.getEmail());
        pdto.setPhoneNumber(p.getPhoneNumber());
        return pdto;
        
    }

    public ResponseEntity<String> DoctorInclusion(Doctor doctor){
        
        if (doctor.getPassword() == null || doctor.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
    
        Doctorrepo.save(doctor);

        return ResponseEntity.ok("The Doctor has been included to the database");
    }

    public MedicalRecordsDto newRecord(MedicalRecordsDto medrec){
        MedicalRecords med = new MedicalRecords();
        med.setSymptoms(medrec.getSymptoms());
        med.setDiagnosis(medrec.getDiagnosis());
        med.setPrescription(medrec.getPrescription());
        med.setDate(LocalDate.now());
        
        Patient p = Patientrepo.findById(medrec.getPatientId()).orElseThrow(()-> new PatientNotFoundException("Patient not found!"));
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
    public List<Patient> totalPatient(String user){
        Doctor doc = Doctorrepo.findByEmailId(user).orElseThrow(()->new DoctorNotFoundException("Doctor Not found!"));
        return doc.getList();
    }

    public PatientDto singlePatient(long DoctorId,long PatientId){
        Patient p =  Patientrepo.GetPatientDetails(DoctorId,PatientId).orElseThrow(()->new PatientNotFoundException("NO PATIENT"));
        PatientDto pdto = new PatientDto();
        pdto.setName(p.getName());
        pdto.setEmail(p.getEmail());
        pdto.setPhoneNumber(p.getPhoneNumber());

        return pdto;
    }

    public List<MedicalRecords> totalMedrecord(long doctorId, long patientId){
        List<MedicalRecords> med = MedicalRecordsrepo.GetMedicalRecords(doctorId,patientId,LocalDate.now());
        
        if(med.isEmpty()){
            throw new MedicalRecordNotFoundException("No Medical records exists for the paitent!");
        }

        return med;
    }

    public List<DoctorDto> totalDoctor(){
        List<Doctor> docs =  Doctorrepo.findAll();

        List<DoctorDto> dto = new ArrayList<>();

        for(Doctor i : docs){
            DoctorDto d = new DoctorDto();
            d.setName(i.getName());
            d.setPhoneNumber(i.getPhoneNumber());
            d.setSpecialization(i.getSpecializaion());

            dto.add(d);
        }

        return dto;
    }

    public ResponseEntity<String> updateDoctor(long DoctorId,DoctorDto doctorDto){
        Doctor doc = Doctorrepo.findById(DoctorId).orElseThrow(()-> new DoctorNotFoundException("Could not find ID "+DoctorId));

        doc.setName(doctorDto.getName());
        doc.setProfession(doctorDto.getProfession());
        doc.setPhoneNumber(doctorDto.getPhoneNumber());

        Doctorrepo.save(doc);
        return ResponseEntity.ok("The Doctor details have been Successfully updated!");
    }

    public ResponseEntity<String> updatePatient(long PatientId,PatientDto patientDto){
        Patient patient = Patientrepo.GetPatientDetails(patientDto.getDoctorId(),PatientId).orElseThrow(()->new PatientNotFoundException("Patient not found!"));

        patient.setEmail(patientDto.getEmail());
        patient.setPhoneNumber(patientDto.getPhoneNumber());

        Patientrepo.save(patient);

        return ResponseEntity.ok("The Patient of ID "+PatientId+" has been successfully updated!");

    }

    public ResponseEntity<String> DeletePatient(long DoctorId,long PatientId){
        Patient patient = Patientrepo.GetPatientDetails(DoctorId,PatientId).orElseThrow(()->new PatientNotFoundException("Patient not found!"));
        List<MedicalRecords> medrec = patient.getMedicalRecords();

        for(MedicalRecords i : medrec){
            MedicalRecordsrepo.deleteById(i.getId());
        }

        Patientrepo.deleteById(PatientId);

        return ResponseEntity.ok("The Patient of ID "+PatientId+" Has been deleted along with all their records!");
    }

    public ResponseEntity<String> DeleteDoctor(long DoctorId, long NewDoctorId){
        Doctor doc = Doctorrepo.findById(DoctorId).orElseThrow(()-> new DoctorNotFoundException("Could not find ID "+DoctorId));
        List<Patient> p = doc.getList();
        Doctor D = Doctorrepo.findById(NewDoctorId).orElseThrow(()-> new DoctorNotFoundException("Could not find Replacement Doctor ID "+NewDoctorId));
        for(Patient i : p){
            i.setDoctor(D);
            Patientrepo.save(i);
        }

        Doctorrepo.delete(doc);

        return ResponseEntity.ok("The Doctor of ID "+DoctorId+" Has been deleted and Doctor with ID "+NewDoctorId+" has been replaced successfully!");
    }
}

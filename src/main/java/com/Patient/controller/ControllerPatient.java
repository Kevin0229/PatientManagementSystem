package com.Patient.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Patient.DTOclasses.DoctorDto;
import com.Patient.DTOclasses.MedicalRecordsDto;
import com.Patient.DTOclasses.PatientDto;
import com.Patient.model.Doctor;
import com.Patient.model.MedicalRecords;
import com.Patient.model.Patient;
import com.Patient.service.PatientService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/Doctor")
public class ControllerPatient {
    @Autowired
    PatientService patientServices;

    // @GetMapping("/form")
    // public String formProvider(Model model){
    //     model.addAttribute("DTOpatient",new PatientDto());
    //     return "index";
    // }
    

    @PostMapping("/NewPatient")
    public ResponseEntity<PatientDto> addPatientToDoctor(@RequestBody @Valid PatientDto patients){
        return ResponseEntity.ok(patientServices.newPatientAdd(patients));
        
    }

    @PostMapping("/Newdoctor")
    public  ResponseEntity<Doctor> addDoctorDetails(@RequestBody @Valid DoctorDto doctor){
        return ResponseEntity.ok(patientServices.DoctorInclusion(doctor));
    }

    @PostMapping("/NewMedicalRecord")
    public  ResponseEntity<MedicalRecordsDto> addMedicalRecord(@RequestBody @Valid MedicalRecordsDto medrec){
        return ResponseEntity.ok(patientServices.newRecord(medrec));
    }


    @GetMapping("/{DoctorId}/Patient")
    public ResponseEntity<List<Patient>> recieveAllPatient(@PathVariable long DoctorId){
        List<Patient> patients = patientServices.totalPatient(DoctorId);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{DoctorId}/Patient/{PatientId}")
    public ResponseEntity<PatientDto> recieveAPatient(@PathVariable long DoctorId,@PathVariable long PatientId){
        PatientDto patient =patientServices.singlePatient(DoctorId,PatientId);
        return ResponseEntity.ok(patient);
    }

    
    @GetMapping("/{DoctorId}/Patient/{PatientId}/MedicalRecords")
    public ResponseEntity<List<MedicalRecords>> recieveAllMedicalRecords(@PathVariable long DoctorId){
        List<MedicalRecords> record = patientServices.totalMedrecord(DoctorId, DoctorId);
        return ResponseEntity.ok(record);
    }
   
    @GetMapping("/All")
    public ResponseEntity<List<DoctorDto>> allDoctor(){
        List<DoctorDto> doctors = patientServices.totalDoctor();
        return ResponseEntity.ok(doctors);
    }

    @PutMapping("/{DoctorId}")
    public ResponseEntity<String> DoctorUpdate(@PathVariable long DoctorId, @RequestBody DoctorDto doctorDto){
        return patientServices.updateDoctor(DoctorId,doctorDto);
    }
     
    @PutMapping("/Patient/{PatientId}")
    public ResponseEntity<String> PatientUpdate(@PathVariable long PatientId, @RequestBody PatientDto patientDto){
        return patientServices.updatePatient(PatientId,patientDto);
    }

    @DeleteMapping("/{DoctorId}/Patient/{PatientId}")
    public ResponseEntity<String> patientDelete(@PathVariable long DoctorId,@PathVariable long PatientId){
        return patientServices.DeletePatient(DoctorId,PatientId);
    }
   
    @DeleteMapping("/{DoctorId}/NewDoctor/{NewDoctorId}")
    public ResponseEntity<String> doctorDelete(@PathVariable long DoctorId,@PathVariable long NewDoctorId){
        return patientServices.DeleteDoctor(DoctorId, NewDoctorId);
    }

    

}
package com.Patient.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Patient.DTOclasses.DoctorDto;
import com.Patient.DTOclasses.MedicalRecordsDto;
import com.Patient.DTOclasses.PatientDto;
import com.Patient.model.Doctor;
import com.Patient.model.Patient;
import com.Patient.service.PatientService;






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
    public PatientDto addPatientToDoctor(@RequestBody PatientDto patients){
        return patientServices.newPatientAdd(patients);
        
    }

    @PostMapping("/Newdoctor")
    public Doctor addDoctorDetails(@RequestBody DoctorDto doctor){
        return patientServices.DoctorInclusion(doctor);
    }

    @PostMapping("/NewMedicalRecord")
    public MedicalRecordsDto addMedicalRecord(@RequestBody MedicalRecordsDto medrec){
        return patientServices.newRecord(medrec);
    }


    @GetMapping("/{DoctorId}/Patient")
    public List<Patient> recieveAllPatient(@PathVariable long DoctorId){
        return patientServices.totalPatient(DoctorId);
    }

    @GetMapping("/{DoctorId}/Patient/{PatientId}")
    public PatientDto recieveAPatient(@PathVariable long DoctorId,@PathVariable long PatientId){
        return patientServices.singlePatient(DoctorId,PatientId);
    }

    
    // @GetMapping("/{DoctorId}/Patient/{PatientId}/MedicalRecords")
    // @GetMapping("/{DoctorId}/Patient/{PatientId}LatestRecord")

}
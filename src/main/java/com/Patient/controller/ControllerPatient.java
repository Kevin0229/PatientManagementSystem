package com.Patient.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Patient.DTOclasses.DoctorDto;
import com.Patient.DTOclasses.MedicalRecordsDto;
import com.Patient.DTOclasses.PatientDto;
import com.Patient.model.Doctor;
import com.Patient.service.PatientService;






@RestController
@RequestMapping("/Patient")
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

    @PostMapping("/NewdoctorDetail")
    public Doctor addDoctorDetails(@RequestBody DoctorDto doctor){
        return patientServices.DoctorInclusion(doctor);
    }

    @PostMapping("/NewMedicalRecord")
    public MedicalRecordsDto addMedicalRecord(@RequestBody MedicalRecordsDto medrec){
        return patientServices.newRecord(medrec);
    }

}
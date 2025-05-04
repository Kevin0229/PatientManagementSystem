package com.Patient.PatientManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.glassfish.jaxb.runtime.v2.schemagen.xmlschema.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Patient.DTOclasses.MedicalRecordsDto;
import com.Patient.DTOclasses.PatientDto;
import com.Patient.model.Doctor;
import com.Patient.model.Patient;
import com.Patient.repository.DoctorRepo;
import com.Patient.repository.MedicalRecordsRepo;
import com.Patient.repository.PatientRepo;
import com.Patient.service.PatientService;

@ExtendWith(MockitoExtension.class)
class PatientManagementApplicationTests {
	@Mock
    PatientRepo Patientrepo;

    @Mock
    DoctorRepo Doctorrepo;

    @Mock
    MedicalRecordsRepo MedicalRecordsrepo;

	@Mock
	PasswordEncoder passwordEncoder;

	@InjectMocks
	PatientService patientServices;

	@Test
	void newPatientAddedOrNot() {
		String username = "Someone@gmail.com";

		PatientDto pdto = new PatientDto();
		pdto.setName("relox");
		pdto.setEmail("rolex@gmail.com");
		pdto.setPhoneNumber("9177736351");

		Doctor doctor = new Doctor();
		doctor.setEmailId(username);

		when(Doctorrepo.findByEmailId(username)).thenReturn(Optional.of(doctor));

		ResponseEntity<String> res= patientServices.newPatientAdd(username,pdto);

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals("The Patient has been added to the database successfully!", res.getBody());
	}

	// @Test
	// void doctorAddedOrNot(){
	// 	Doctor doctor = new Doctor();
	// 	doctor.setPassword("1234");

	// 	when(passwordEncoder.encode(doctor.getPassword())).thenReturn();
	// 	DoctorInclusion(Doctor doctor)
	// }
	
	@Test
	void MedicalRecordAddedOrNot(){
		String username = "Someone@gmail.com";
		long patientId = 1;
		MedicalRecordsDto mdto = new MedicalRecordsDto();
		mdto.setSymptoms("eye pain");
		mdto.setDiagnosis("eye pressure");
		mdto.setPrescription("saline water rinse");

		Doctor doctor = new Doctor();
		doctor.setEmailId(username);
		
		Patient patient = new Patient();
		patient.setId((long) 1);
		patient.setDoctor(doctor);

		when(Patientrepo.GetPatientDetails(username, patientId)).thenReturn(Optional.of(patient));


		ResponseEntity<String> res = patientServices.newRecord( mdto,patientId,username);

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals("The Medical Record for Patient "+patientId+" has been added to the database successfully", res.getBody());

	}

	
	
	

}

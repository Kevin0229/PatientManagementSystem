package com.Patient.PatientManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.Patient.DTOclasses.PatientDto;
import com.Patient.model.Doctor;
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


	@InjectMocks
	PatientService patientServices;

	@Test
	void contextLoads() {
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

}

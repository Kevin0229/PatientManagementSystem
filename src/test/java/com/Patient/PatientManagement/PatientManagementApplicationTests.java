package com.Patient.PatientManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javax.print.Doc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Patient.DTOclasses.DoctorDto;
import com.Patient.DTOclasses.MedicalRecordsDto;
import com.Patient.DTOclasses.PatientDto;
import com.Patient.controller.ControllerPatient;
import com.Patient.model.Doctor;
import com.Patient.model.MedicalRecords;
import com.Patient.model.Patient;
import com.Patient.repository.DoctorRepo;
import com.Patient.repository.MedicalRecordsRepo;
import com.Patient.repository.PatientRepo;
import com.Patient.service.PatientService;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

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
	void testNewPatientAdd_returnStatus_whenUserEmailValid() {
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

		verify(Doctorrepo,times(1)).findByEmailId(username);
	}

	@Test
	void testDoctorAdd_returnStatus_whenPasswordProvided(){
		Doctor doctor = new Doctor();
		doctor.setName("Kurtz");
		doctor.setEmailId("one@gmail.com");
		doctor.setSpecialization("eye specialist");
		doctor.setPhoneNumber("9198827771");
		doctor.setPassword("2222");

		String originalPass = "2222";
		String pass = "$2a$10$ZOHcG.QFfVi5RKRx4DUU2OvUL5kZRTDkHQktjnpMOwbl.ITZ4up/m";

		when(passwordEncoder.encode(doctor.getPassword())).thenReturn(pass);

		ResponseEntity<String> res = patientServices.DoctorInclusion(doctor);

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals("The Doctor has been included to the database successfully", res.getBody());
		assertEquals(pass, doctor.getPassword());

		verify(passwordEncoder,times(1)).encode(originalPass);
	}
	
	@Test
	void testMedicalRecordAdd_returnStatus_whenUserAndPatientValid(){
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

		verify(Patientrepo,times(1)).GetPatientDetails(username, patientId);

	}

	@Test
	void testTotalPatient_returnsPatientDto_whenUserEmailValid(){
		String username = "Someone@gmail.com";

		Doctor doctor = new Doctor();
		doctor.setEmailId(username);

		List<Patient> patient = new ArrayList<>();

		Patient p1 = new Patient();
		p1.setName("name1");
		p1.setEmail("name1@gmail.com");
		p1.setPhoneNumber("111111111");

		Patient p2 = new Patient();
		p2.setName("name2");
		p2.setEmail("name2@gmail.com");
		p2.setPhoneNumber("222222222");
		
		patient.add(p1);
		patient.add(p2);
		doctor.setList(patient);

		when(Doctorrepo.findByEmailId(username)).thenReturn(Optional.of(doctor));

		List<PatientDto> res = patientServices.totalPatient(username);

		assertEquals(2, res.size());
		assertEquals("name1", res.get(0).getName());
		assertEquals("name2@gmail.com", res.get(1).getEmail());

		verify(Doctorrepo,times(1)).findByEmailId(username);


	}

	@Test
	void testSinglePatient_returnsPatientDto_whenUserAndpatientValid(){
		String username = "Someone@gmail.com";
		long patientId = 1;

		Doctor doctor = new Doctor();
		doctor.setEmailId(username);

		Patient patient = new Patient();
		patient.setId((long)1);
		patient.setName("Name1");
		patient.setDoctor(doctor);
		patient.setEmail("name1@gmail.com");
		patient.setPhoneNumber("9191929298");
		

		when(Patientrepo.existsById(patientId)).thenReturn(true);
		when(Patientrepo.GetPatientDetails(username,patientId)).thenReturn(Optional.of(patient));

		PatientDto res = patientServices.singlePatient(username,patientId);

		assertEquals("Name1", res.getName());
		assertEquals("name1@gmail.com", res.getEmail());

		verify(Patientrepo,times(1)).existsById(patientId);
		verify(Patientrepo,times(1)).GetPatientDetails(username,patientId);
	}

	@Test
	void testTotalMedicalRecords_returnsMedicalRecordsList_(){
		String username = "Someone@gmail.com";
		long patientId = 1;

		Doctor doctor = new Doctor();
		doctor.setEmailId(username);

		Patient patient = new Patient();
		patient.setId((long)1);
		patient.setName("Name1");
		patient.setDoctor(doctor);
		patient.setEmail("name1@gmail.com");
		patient.setPhoneNumber("9191929298");

		List<MedicalRecords> medrec = new ArrayList<>();

		MedicalRecords med1 = new MedicalRecords();

		med1.setSymptoms("symptom1");
		med1.setDiagnosis("diagnosis1");
		med1.setPrescription("prescription1");
		med1.setDate(LocalDate.now());;
		med1.setPatient(patient);

		MedicalRecords med2 = new MedicalRecords();

		med2.setSymptoms("symptom2");
		med2.setDiagnosis("diagnosis2");
		med2.setPrescription("prescription2");
		med2.setDate(LocalDate.now());;
		med2.setPatient(patient);

		medrec.add(med1);
		medrec.add(med2);

		when(Patientrepo.GetPatientDetails(username,patientId)).thenReturn(Optional.of(patient));
		when(MedicalRecordsrepo.GetMedicalRecords(username,patientId,LocalDate.now())).thenReturn(medrec);
		

		List<MedicalRecords> res = patientServices.totalMedrecord(username,patientId);

		assertEquals(2, res.size());
		assertEquals("symptom1", res.get(0).getSymptoms());
		assertEquals("prescription2", res.get(1).getPrescription());

		verify(Patientrepo,times(1)).GetPatientDetails(username,patientId);
		verify(MedicalRecordsrepo,times(1)).GetMedicalRecords(username,patientId,LocalDate.now());
	}
	@Test
	void testAllDoctor_returnsDoctorDtoList(){
		List<Doctor> doctors = new ArrayList<>();
		Doctor d1 = new Doctor();
        d1.setName("Dr. Shawn");
        d1.setPhoneNumber("1234567890");
        d1.setSpecialization("Surgery");
		Doctor d2= new Doctor();
		d2.setName("Dr. Aaron");
		d2.setPhoneNumber("0987654321");
		d2.setSpecialization("Neurology");
		doctors.add(d1);
		doctors.add(d2);

		when(Doctorrepo.findAll()).thenReturn(doctors);
		List<DoctorDto> res = patientServices.totalDoctor();
		assertEquals(2, res.size());
		assertEquals("Dr. Shawn", res.get(0).getName());
		assertEquals("0987654321", res.get(1).getPhoneNumber());
		assertEquals("Surgery", res.get(0).getSpecialization());
		assertEquals("Neurology", res.get(1).getSpecialization());
		verify(Doctorrepo,times(1)).findAll();

	}
	
	 


}

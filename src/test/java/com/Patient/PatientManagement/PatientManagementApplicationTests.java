package com.Patient.PatientManagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PatientManagementApplicationTests {

	@Test
	void contextLoads() {
	}

}

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PatientRecordManagementSystemTest {

    private PatientRecordManagementSystem system;

    @BeforeEach
    public void setUp() {
        system = new PatientRecordManagementSystem();
    }

    @Test
    public void testAddAndRetrievePatient() {
        Patient patient = new Patient("123", "John Doe");
        system.addPatient(patient);

        Patient retrieved = system.getPatient("123");
        assertNotNull(retrieved);
        assertEquals("John Doe", retrieved.getName());
    }

    @Test
    public void testDeletePatient() {
        Patient patient = new Patient("123", "John Doe");
        system.addPatient(patient);

        boolean deleted = system.deletePatient("123");
        assertTrue(deleted);
        assertNull(system.getPatient("123"));
    }

    @Test
    public void testGetNonExistentPatient() {
        assertNull(system.getPatient("999"));
    }
}

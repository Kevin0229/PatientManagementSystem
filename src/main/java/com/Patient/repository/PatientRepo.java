package com.Patient.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Patient.model.Patient;


@Repository
public interface PatientRepo extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE p.id = :PatientId AND p.doctor.emailId = :user")
    Optional<Patient> GetPatientDetails(@Param("user") String user,@Param("PatientId") long PatientId);
}
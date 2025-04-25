package com.Patient.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Patient.model.MedicalRecords;

@Repository
public interface MedicalRecordsRepo extends JpaRepository<MedicalRecords, Long> {

    @Query("SELECT r FROM MedicalRecords r WHERE r.date <= :localdate AND r.patient.id = :PatientId AND r.patient.doctor.id = :DoctorId ORDER BY r.date DESC")
    List<MedicalRecords> GetMedicalRecords(@Param("DoctorId") long DoctorId,@Param("PatientId") long PatientId,@Param("localdate") LocalDate localdate);

}

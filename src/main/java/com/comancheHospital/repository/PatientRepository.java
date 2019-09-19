package com.comancheHospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comancheHospital.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{

}

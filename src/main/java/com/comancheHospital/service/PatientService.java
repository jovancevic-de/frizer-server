package com.comancheHospital.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.comancheHospital.model.Patient;
import com.comancheHospital.repository.PatientRepository;

@Service
public class PatientService {
	
	private final PatientRepository patientRepository;

	public PatientService(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}
	
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}
	
	public Patient getOnePatient(Long id) {
		return patientRepository.findById(id).get();
	}

}

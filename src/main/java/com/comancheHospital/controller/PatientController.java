package com.comancheHospital.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comancheHospital.dto.PatientDto;
import com.comancheHospital.model.Patient;
import com.comancheHospital.service.PatientService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	private final PatientService patientService;

	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}
	
	@GetMapping("/patients")
	public ResponseEntity<List<PatientDto>> getAllPatients() {
		List<Patient> patients = patientService.getAllPatients();
		
		List<PatientDto> patientsDto = new ArrayList<>();
		
		for(Patient patient: patients) {
			PatientDto patientDto = modelMapper.map(patient, PatientDto.class);
			patientsDto.add(patientDto);
		}
		
		return ResponseEntity.ok().body(patientsDto);
	}

}

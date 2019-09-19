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

import com.comancheHospital.dto.DoctorDto;
import com.comancheHospital.model.Doctor;
import com.comancheHospital.service.DoctorService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	private final DoctorService doctorService;

	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
	
	@GetMapping("/doctors")
	public ResponseEntity<List<DoctorDto>> getAllDoctors() {
		List<Doctor> doctors = doctorService.getDoctors();
		List<DoctorDto> doctorDtos = new ArrayList<>();
		
		for(Doctor doctor : doctors) {
			DoctorDto doctorDto = modelMapper.map(doctor, DoctorDto.class);
			doctorDtos.add(doctorDto);
		}
		
		return ResponseEntity.ok().body(doctorDtos);
	}

}

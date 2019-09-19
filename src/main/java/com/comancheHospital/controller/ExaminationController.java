package com.comancheHospital.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comancheHospital.dto.ExaminationDto;
import com.comancheHospital.dto.PageDto;
import com.comancheHospital.model.Doctor;
import com.comancheHospital.model.Examination;
import com.comancheHospital.model.Patient;
import com.comancheHospital.service.DoctorService;
import com.comancheHospital.service.ExaminationService;
import com.comancheHospital.service.PatientService;

@RestController
@RequestMapping("/api")
public class ExaminationController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private DoctorService doctorService;
	
	private final ExaminationService examinationService;

	public ExaminationController(ExaminationService examinationService) {
		this.examinationService = examinationService;
	}
	
	@GetMapping("/examinations")
	public ResponseEntity<List<ExaminationDto>> getAllExaminations() {
		List<Examination> examinations = examinationService.getAllExaminations();
		List<ExaminationDto> examinationDtos = new ArrayList<>();
		
		for(Examination examination : examinations) {
			ExaminationDto examinationDto = modelMapper.map(examination, ExaminationDto.class);
			examinationDtos.add(examinationDto);
		}
		
		return ResponseEntity.ok().body(examinationDtos);
	}
	
	@GetMapping("/doctors/{doctorId}/examinations")
	public ResponseEntity<PageDto<ExaminationDto>> getAllExaminationsByADoctor(Pageable pageable, @PathVariable Long doctorId) {
		Page<Examination> examinationsByDoctor = examinationService.getAllExaminationsByDoctorWithPage(pageable, doctorId);
		
		List<ExaminationDto> examinationDtos = new ArrayList<>();
		
		for(Examination examination: examinationsByDoctor) {
			ExaminationDto examinationDto = modelMapper.map(examination, ExaminationDto.class);
			examinationDtos.add(examinationDto);
		}
		
		PageDto<ExaminationDto> examinationsByDoctorByPage = new PageDto<>(pageable.getPageNumber(), 
				pageable.getPageSize(), examinationsByDoctor.getTotalElements(), examinationDtos);
		
		return ResponseEntity.ok().body(examinationsByDoctorByPage);
	}
	
	@PostMapping("/addExamination")
	public ResponseEntity<ExaminationDto> saveExamination(@RequestBody ExaminationDto examinationDto) {
		Patient patient = patientService.getOnePatient(examinationDto.getPatient().getId());
		Doctor doctor = doctorService.getOneDoctor(examinationDto.getDoctor().getId());
		
		Examination examination = new Examination();
		examination.setDoctor(doctor);
		examination.setPatient(patient);
		examination.setExamination_date(examinationDto.getExaminationDate());
		
		examination = examinationService.saveExamination(examination);
		
		ExaminationDto newExamination = modelMapper.map(examination, ExaminationDto.class);
		
		return new ResponseEntity<>(newExamination, HttpStatus.CREATED);
	}

}

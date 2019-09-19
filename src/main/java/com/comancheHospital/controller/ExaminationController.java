package com.comancheHospital.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@CrossOrigin(origins = "http://localhost:4200")
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

		for (Examination examination : examinations) {
			ExaminationDto examinationDto = modelMapper.map(examination, ExaminationDto.class);
			examinationDtos.add(examinationDto);
		}

		return ResponseEntity.ok().body(examinationDtos);
	}

	@GetMapping("/examinations/{id}")
	public ResponseEntity<ExaminationDto> getOneExamination(@PathVariable Long id) {
		Examination examination = examinationService.getOneExamination(id);

		ExaminationDto examinationDto = modelMapper.map(examination, ExaminationDto.class);

		return ResponseEntity.ok().body(examinationDto);
	}

	@GetMapping("/doctors/{doctorId}/listExaminations")
	public ResponseEntity<List<ExaminationDto>> getExaminationsByDoctor(@PathVariable Long doctorId) {
		List<Examination> examinations = examinationService.getAllExaminationsByDoctor(doctorId);

		List<ExaminationDto> examinationDtos = new ArrayList<>();

		for (Examination examination : examinations) {
			ExaminationDto examinationDto = modelMapper.map(examination, ExaminationDto.class);
			examinationDtos.add(examinationDto);
		}
		
		return ResponseEntity.ok().body(examinationDtos);
	}

	@GetMapping("/doctors/{doctorId}/examinations")
	public ResponseEntity<PageDto<ExaminationDto>> getAllExaminationsByADoctor(Pageable pageable,
			@PathVariable Long doctorId) {
		Page<Examination> examinationsByDoctor = examinationService.getAllExaminationsByDoctorWithPage(pageable,
				doctorId);

		List<ExaminationDto> examinationDtos = new ArrayList<>();

		for (Examination examination : examinationsByDoctor) {
			ExaminationDto examinationDto = modelMapper.map(examination, ExaminationDto.class);
			examinationDtos.add(examinationDto);
		}

		PageDto<ExaminationDto> examinationsByDoctorByPage = new PageDto<>(pageable.getPageNumber(),
				pageable.getPageSize(), examinationsByDoctor.getTotalElements(), examinationDtos);

		return ResponseEntity.ok().body(examinationsByDoctorByPage);
	}

	@PostMapping("/examinations")
	public ResponseEntity<?> saveExamination(@RequestBody ExaminationDto examinationDto) {
		Patient patient = patientService.getOnePatient(examinationDto.getPatient().getId());
		Doctor doctor = doctorService.getOneDoctor(examinationDto.getDoctor().getId());

		Examination examination = new Examination();
		examination.setDoctor(doctor);
		examination.setPatient(patient);
		examination.setExaminationDate(examinationDto.getExaminationDate());
		List<Examination> examinationsByDate = examinationService
				.getExaminationByDate(examinationDto.getExaminationDate());

		if (examinationDto.getExaminationDate().getHour() < 8 || examinationDto.getExaminationDate().getHour() > 15) {
			return new ResponseEntity<>("Examination can't be scheduled! Date and time already taken.", HttpStatus.BAD_REQUEST);
		}
		for (Examination examinationByDate : examinationsByDate)
			if (examinationDto.getExaminationDate().equals(examinationByDate.getExaminationDate())) {
				return new ResponseEntity<>("Examination can't be scheduled! Date and time already taken.",
						HttpStatus.BAD_REQUEST);
			}

		examination = examinationService.saveExamination(examination);

		ExaminationDto newExamination = modelMapper.map(examination, ExaminationDto.class);

		return new ResponseEntity<>(newExamination, HttpStatus.CREATED);
	}

	@PutMapping("/examinations/{id}")
	public ResponseEntity<ExaminationDto> updateExamination(@RequestBody ExaminationDto examinationDto,
			@PathVariable Long id) {
		Patient patient = patientService.getOnePatient(examinationDto.getPatient().getId());
		Doctor doctor = doctorService.getOneDoctor(examinationDto.getDoctor().getId());

		Examination examination = examinationService.getOneExamination(id);

		examination.setDoctor(doctor);
		examination.setPatient(patient);
		examination.setExaminationDate(examinationDto.getExaminationDate());

		examination = examinationService.saveExamination(examination);

		ExaminationDto newExamination = modelMapper.map(examination, ExaminationDto.class);

		return ResponseEntity.ok().body(newExamination);
	}

	@DeleteMapping("/deleteExamination/{id}")
	public ResponseEntity<Object> deleteExamination(@PathVariable Long id) {
		Examination examination = examinationService.getOneExamination(id);

		if (examination != null) {
			examinationService.deleteExamination(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}

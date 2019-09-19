package com.comancheHospital.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.comancheHospital.model.Examination;
import com.comancheHospital.repository.ExaminationRepository;

@Service
public class ExaminationService {
	
	private final ExaminationRepository examinationRepository;

	public ExaminationService(ExaminationRepository examinationRepository) {
		this.examinationRepository = examinationRepository;
	}
	
	public List<Examination> getAllExaminations() {
		return examinationRepository.findAll();
	}
	
	public List<Examination> getAllExaminationsByDoctor(Long id) {
		return examinationRepository.findByDoctorId(id);
	}
	
	public Page<Examination> getExaminationsByPage(Pageable pageable) {
		return examinationRepository.findAll(pageable);
	}
	
	public Page<Examination> getAllExaminationsByDoctorWithPage(Pageable pageable, Long id) {
		return examinationRepository.findByDoctorId(pageable, id);
	}
	
	public Examination getOneExamination(Long id) {
		return examinationRepository.findById(id).get();
	}
	
	public Examination saveExamination(Examination examination) {
		return examinationRepository.save(examination);
	}

}

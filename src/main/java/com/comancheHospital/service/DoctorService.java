package com.comancheHospital.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.comancheHospital.model.Doctor;
import com.comancheHospital.repository.DoctorRepository;

@Service
public class DoctorService {
	
	private final DoctorRepository doctorRepository;

	public DoctorService(DoctorRepository doctorRepository) {
		this.doctorRepository = doctorRepository;
	}
	
	public List<Doctor> getDoctors() {
		return doctorRepository.findAll();
	}
	
	public Page<Doctor> getDoctorsByPage(Pageable pageable) {
		return doctorRepository.findAll(pageable);
	}
	
	public Doctor getOneDoctor(Long id) {
		return doctorRepository.findById(id).get();
	}

}

package com.comancheHospital.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comancheHospital.model.Examination;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {
	
	List<Examination> findByDoctorId(Long id);
	Page<Examination> findByDoctorId(Pageable pageable, Long id);
	
}

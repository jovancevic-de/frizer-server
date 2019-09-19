package com.comancheHospital.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExaminationDto {
	
	private Long id;
	
	private DoctorDto doctor;
	
	private PatientDto patient;
	
	private LocalDateTime examinationDate;

}

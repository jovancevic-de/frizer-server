package com.comancheHospital.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime examinationDate;

}

package com.comancheHospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
	
	private Long id;
	
	private String firstName;
	
	private String lastName;

}

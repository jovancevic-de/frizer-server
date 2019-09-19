package com.comancheHospital.dto;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {
	
	private int currentPage;
	private int itemsPerPage;
	private long totalItems;
	private Collection<T> items;
}

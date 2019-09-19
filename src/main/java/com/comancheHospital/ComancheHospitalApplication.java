package com.comancheHospital;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
public class ComancheHospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComancheHospitalApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public ErrorViewResolver supportPathBasedLocationStrategyWithoutHashes() {
		return new ErrorViewResolver() {
			
			@Override
			public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
				return status == HttpStatus.NOT_FOUND ?
						new ModelAndView("index.html", Collections.emptyMap(), HttpStatus.OK) : null;
			}
		};
	}
}

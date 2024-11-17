package com.student.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class StudentsListDTO {
	
	@JsonProperty(value= "studentsDto")
	private List<StudentsDTO> studentsListDto;

	public List<StudentsDTO> getStudentsListDto() {
		return studentsListDto;
	}

	public void setStudentsListDto(List<StudentsDTO> studentsListDto) {
		this.studentsListDto = studentsListDto;
	}

	
	
	
	
}

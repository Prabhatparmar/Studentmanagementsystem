package com.student.dto;


import com.fasterxml.jackson.annotation.JsonProperty;


public class StudentsDTO {
	
	@JsonProperty(value= "students")
	private StudentDTO studentDto;

	public StudentDTO getStudentDto() {
		return studentDto;
	}

	public void setStudentDto(StudentDTO studentDto) {
		this.studentDto = studentDto;
	}


	
	
}

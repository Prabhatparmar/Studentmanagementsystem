package com.student.dto;


import com.fasterxml.jackson.annotation.JsonProperty;


public class DepartmentDTO {
	@JsonProperty(value= "departmentId")
    private int departmentId;
	
	@JsonProperty(value= "departmentName")
    private String departmentName;
	
	@JsonProperty(value= "headOfDepartment")
    private String headOfDepartment;

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getHeadOfDepartment() {
		return headOfDepartment;
	}

	public void setHeadOfDepartment(String headOfDepartment) {
		this.headOfDepartment = headOfDepartment;
	}
	
	

}
package com.student.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentDTO {
	
	@JsonProperty(value= "studentId")
	private int studentId;
	
	@JsonProperty(value= "firstName")
    private String firstName;
	
	@JsonProperty(value= "lastName")
    private String lastName;
	
	@JsonProperty(value= "dateOfBirth")
    private String dateOfBirth;
	
	@JsonProperty(value= "contactNumber")
    private String contactNumber;
	
	@JsonProperty(value= "email")
    private String email;
	
	@JsonProperty(value= "address")
    private String address;
	
	@JsonProperty(value= "admissionDate")
    private String admissionDate;
	
	@JsonProperty(value= "department")
    private DepartmentDTO department;

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}

	public DepartmentDTO getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentDTO department) {
		this.department = department;
	}
	
	

}

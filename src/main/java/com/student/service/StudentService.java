package com.student.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.student.dto.StudentDTO;
import com.student.dto.StudentsDTO;
import com.student.dto.StudentsListDTO;
import com.student.entity.Department;
import com.student.entity.Students;
import com.student.repository.DepartmentRepository;
import com.student.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private static final Logger log = LoggerFactory.getLogger(StudentService.class);

	@Transactional
	public ResponseEntity<String> saveStudents(StudentsListDTO studentsListDTO) {
		log.info("Started saving students");

		List<Students> studentsList = new ArrayList<>();
		StringBuilder departmentErrorMessages = new StringBuilder();

		for (StudentsDTO studentDTO : studentsListDTO.getStudentsListDto()) {
			log.debug("Processing student: {}", studentDTO.getStudentDto().getFirstName());

			try {
				Students student = new Students();
				student.setFirst_name(studentDTO.getStudentDto().getFirstName());
				student.setLast_name(studentDTO.getStudentDto().getLastName());

				// Convert String to LocalDate, then to java.sql.Date
				LocalDate localDateOfBirth = LocalDate.parse(studentDTO.getStudentDto().getDateOfBirth(), formatter);
				student.setDate_of_birth(Date.valueOf(localDateOfBirth));

				student.setContact_Number(studentDTO.getStudentDto().getContactNumber());
				student.setEmail(studentDTO.getStudentDto().getEmail());
				student.setAddress(studentDTO.getStudentDto().getAddress());
				student.setAdmission_date(Date.valueOf(studentDTO.getStudentDto().getAdmissionDate()));

				// Ensure that the department exists or return an error message
				if (studentDTO.getStudentDto().getDepartment() != null) {
					Optional<Department> existingDepartment = departmentRepository
							.findById(studentDTO.getStudentDto().getDepartment().getDepartmentId());
					if (existingDepartment.isPresent()) {
						//student.setDepartment(existingDepartment.get());
						log.debug("Found existing department: {}",
								studentDTO.getStudentDto().getDepartment().getDepartmentName());
					} else {
						departmentErrorMessages.append("Department '")
								.append(studentDTO.getStudentDto().getDepartment().getDepartmentName())
								.append("' does not exist. ");
						log.error("Department '{}' does not exist for student {}",
								studentDTO.getStudentDto().getDepartment().getDepartmentName(),
								studentDTO.getStudentDto().getFirstName());
					}
				}

				studentsList.add(student);
				
			} catch (Exception e) {
				log.error("Error processing student: {}", studentDTO.getStudentDto().getFirstName(), e);
			}
		}

		// Check if any department errors occurred
		if (departmentErrorMessages.length() > 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(departmentErrorMessages.toString());
		}

		// If no department errors, save all students
		if (!studentsList.isEmpty()) {
			try {
				studentRepository.saveAll(studentsList);
				log.info("Successfully saved {} students", studentsList.size());
				return ResponseEntity.status(HttpStatus.CREATED).body("Students saved successfully.");
			} catch (Exception e) {
				log.error("Error saving students to the database", e);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("An error occurred while saving students.");
			}
		} else {
			log.warn("No students were processed for saving.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No students to save.");
		}
	}

	// Method to retrieve a student by ID
	public StudentDTO getStudentById(Long id) {
		Optional<Students> optionalStudent = studentRepository.findById(id);
		if (optionalStudent.isPresent()) {
			Students student = optionalStudent.get();
			// Map the Students entity to StudentsDTO
			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setStudentId(student.getStudent_Id());
			studentDTO.setFirstName(student.getFirst_name());
			studentDTO.setLastName(student.getLast_name());
			studentDTO.setDateOfBirth(student.getDate_of_birth().toString()); // Format as needed
			studentDTO.setContactNumber(student.getContact_Number());
			studentDTO.setEmail(student.getEmail());
			studentDTO.setAddress(student.getAddress());
			studentDTO.setAdmissionDate(student.getAdmission_date().toString()); // Format as needed
			// Handle department information if needed
			return studentDTO;
		} else {
			log.warn("Student with ID {} not found", id);
			return null; // Or throw a custom exception
		}
	}

	
	public List<StudentsDTO> getAllStudents() {
	    List<Students> students = studentRepository.findAll(); // Fetch all students
	    List<StudentsDTO> studentsDTOList = new ArrayList<>();

	    for (Students student : students) {
	        StudentsDTO studentsDTO = new StudentsDTO();
	        StudentDTO studentDTO = new StudentDTO();
	        studentDTO.setStudentId(student.getStudent_Id());
	        studentDTO.setFirstName(student.getFirst_name());
	        studentDTO.setLastName(student.getLast_name());
	        studentDTO.setDateOfBirth(student.getDate_of_birth().toString());
	        studentDTO.setContactNumber(student.getContact_Number());
	        studentDTO.setEmail(student.getEmail());
	        studentDTO.setAddress(student.getAddress());
	        studentDTO.setAdmissionDate(student.getAdmission_date().toString());
	        
	        studentsDTO.setStudentDto(studentDTO);
	        // Add other fields as necessary
	        studentsDTOList.add(studentsDTO);
	    }

	    return studentsDTOList;
	}
	
	public boolean deleteStudentById(Long id) {
	    Optional<Students> studentOptional = studentRepository.findById(id);
	    if (studentOptional.isPresent()) {
	        studentRepository.delete(studentOptional.get());
	        return true; // Successfully deleted
	    } else {
	        return false; // Student not found
	    }
	}
	
	public boolean updateStudents(StudentsListDTO studentsListDTO) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Define the formatter for date parsing
	    boolean allUpdatesSuccessful = true; // Track if all updates succeed

	    for (StudentsDTO studentsDTO : studentsListDTO.getStudentsListDto()) {
	        Optional<Students> existingStudentOptional = studentRepository.findById((long) studentsDTO.getStudentDto().getStudentId());

	        if (existingStudentOptional.isPresent()) {
	            Students existingStudent = existingStudentOptional.get();

	            // Update fields with values from studentDTO
	            existingStudent.setFirst_name(studentsDTO.getStudentDto().getFirstName());
	            existingStudent.setEmail(studentsDTO.getStudentDto().getEmail());
	            existingStudent.setLast_name(studentsDTO.getStudentDto().getLastName());

	            // Convert String to LocalDate, then to java.sql.Date
	            LocalDate localDateOfBirth = LocalDate.parse(studentsDTO.getStudentDto().getDateOfBirth(), formatter);
	            existingStudent.setDate_of_birth(Date.valueOf(localDateOfBirth));

	            // Save the updated student to the database
	            studentRepository.save(existingStudent);
	        } else {
	            // If any student is not found, mark the overall update as unsuccessful
	            allUpdatesSuccessful = false;
	        }
	    }

	    return allUpdatesSuccessful; // Returns true only if all students were successfully updated
	}

}
	 


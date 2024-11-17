package com.student.controller;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.dto.StudentDTO;
import com.student.dto.StudentsDTO;
import com.student.dto.StudentsListDTO;
import com.student.service.StudentService;


@RestController
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	//MVC architecture (model - Entity, view - ui file, controller)
	// post - save, put - update, delete - delete, get - data fetch 
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveStudents(@RequestBody StudentsListDTO studentsListDTO) {
        return studentService.saveStudents(studentsListDTO);
    }
	

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StudentsListDTO> getStudentById(@PathVariable Long id) {
		StudentDTO studentDTO = studentService.getStudentById(id);
		
		StudentsDTO studentsDTO = new StudentsDTO();
		studentsDTO.setStudentDto(studentDTO);
		
		StudentsListDTO studentsListDTO = new StudentsListDTO();
		studentsListDTO.setStudentsListDto(Collections.singletonList(studentsDTO));
	    
	    return new ResponseEntity<>(studentsListDTO, HttpStatus.OK);

	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StudentsListDTO> getAllStudents() {
	    List<StudentsDTO> studentDTOs = studentService.getAllStudents(); // Retrieve all students from the service

	    StudentsListDTO studentsListDTO = new StudentsListDTO();
	    studentsListDTO.setStudentsListDto(studentDTOs); // Set the list of students

	    return new ResponseEntity<>(studentsListDTO, HttpStatus.OK); // Return the list with OK status
	}
	
	@DeleteMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deletestudents(@PathVariable Long id){
	    try {
	        boolean isDeleted = studentService.deleteStudentById(id); // Call the service to delete the student
	        if (isDeleted) {
	            return ResponseEntity.status(HttpStatus.OK).body("Student deleted successfully.");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the student.");
	    }
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateStudent(
	        @RequestBody StudentsListDTO studentsListDTO) {
	    
	    try {
	        boolean isUpdated = studentService.updateStudents(studentsListDTO);

	        if (isUpdated) {
	            return ResponseEntity.status(HttpStatus.OK).body("Student updated successfully.");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the student.");
	    }
	}
}
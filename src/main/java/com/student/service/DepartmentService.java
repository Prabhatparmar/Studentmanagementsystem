package com.student.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.student.dto.DepartmentDTO;
import com.student.entity.Department;
import com.student.entity.Students;
import com.student.repository.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	DepartmentRepository departmentRepository;

	public ResponseEntity<String> saveDepartment(DepartmentDTO departmentDTO) {
		Department department= new Department();
		department.setDepartmentName(departmentDTO.getDepartmentName());
		department.setHod(departmentDTO.getHeadOfDepartment());
		
		departmentRepository.save(department);
		return ResponseEntity.status(HttpStatus.CREATED).body("Department saved successfully.");

	}

	public List<DepartmentDTO> getAllDepartments() {
		// Retrieve all Department entities from the repository
        List<Department> departments = departmentRepository.findAll();

		List<DepartmentDTO> departmentDTOs = new ArrayList<>();

        // Convert each Department entity to a DepartmentDTO using a for loop
        for (Department department : departments) {
        	DepartmentDTO dto = new DepartmentDTO();
            dto.setDepartmentId(department.getDepartmentId());
            dto.setDepartmentName(department.getDepartmentName());
            dto.setHeadOfDepartment(department.getHod());  

            departmentDTOs.add(dto); // Add the converted DTO to the list
        }

        return departmentDTOs; // Return the list of DepartmentDTOs

	}

	public DepartmentDTO getDepartmentById(Integer id) {
        Optional<Department> departmentOpt = departmentRepository.findById(id);

        // If the department is found, convert it to a DepartmentDTO and return it; otherwise, return null
        if (departmentOpt.isPresent()) {
            Department department = departmentOpt.get();
            DepartmentDTO dto = new DepartmentDTO();
            dto.setDepartmentId(department.getDepartmentId());
            dto.setDepartmentName(department.getDepartmentName());
            dto.setHeadOfDepartment(department.getHod());
            return dto;
        } else {
            return null; // Handle the case where the department is not found
        }
    }

	public boolean updateDepartment(DepartmentDTO departmentDTO) {
		
		boolean allUpdatesSuccessful = true; // Track if all updates succeed
		
		Optional<Department> existingDepartmentOptional = departmentRepository.findById(departmentDTO.getDepartmentId());
		if (existingDepartmentOptional.isPresent()) {
			Department existingDepartment = existingDepartmentOptional.get();

            // Update fields with values from DepartmentDTO
			existingDepartment.setDepartmentName(departmentDTO.getDepartmentName());
			existingDepartment.setHod(departmentDTO.getHeadOfDepartment());
		
            // Save the updated Department to the database
            departmentRepository.save(existingDepartment);
        } else {
            // If any Department is not found, mark the overall update as unsuccessful
            allUpdatesSuccessful = false;
        }
    
    return allUpdatesSuccessful; // Returns true only if all Department were successfully updated
	}
}


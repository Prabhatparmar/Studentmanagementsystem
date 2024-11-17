package com.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    // Custom queries can be added if needed
	
	Optional<Department> findByDepartmentName(String departmentName);
	
}

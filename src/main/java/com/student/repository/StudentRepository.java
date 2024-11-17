package com.student.repository;

import org.springframework.stereotype.Repository;
import com.student.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface StudentRepository extends JpaRepository<Students, Long> {

}

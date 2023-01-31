package com.jsp.SpringBootCRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.SpringBootCRUD.Dto.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
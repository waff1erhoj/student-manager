package com.example.student.service;

import com.example.student.entity.Student;
import java.util.List;

public interface StudentService {
    List<Student> findAll();
    Student findById(int id);
    List<Student> search(String keyword);
    int add(Student student) throws IllegalArgumentException;
    int update(Student student) throws IllegalArgumentException;
    int delete(int id);
}

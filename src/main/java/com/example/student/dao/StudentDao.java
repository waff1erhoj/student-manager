package com.example.student.dao;

import com.example.student.entity.Student;
import java.util.List;

public interface StudentDao {
    List<Student> findAll();
    Student findById(int id);
    List<Student> search(String keyword);
    int insert(Student student);
    int update(Student student);
    int delete(int id);
}

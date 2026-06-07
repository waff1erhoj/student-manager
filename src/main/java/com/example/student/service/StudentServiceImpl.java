package com.example.student.service;

import com.example.student.dao.StudentDao;
import com.example.student.dao.StudentDaoImpl;
import com.example.student.entity.Student;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao = new StudentDaoImpl();

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public Student findById(int id) {
        return studentDao.findById(id);
    }

    @Override
    public List<Student> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return studentDao.findAll();
        }
        return studentDao.search(keyword.trim());
    }

    @Override
    public int add(Student s) throws IllegalArgumentException {
        validate(s);
        return studentDao.insert(s);
    }

    @Override
    public int update(Student s) throws IllegalArgumentException {
        if (s.getId() == null || s.getId() <= 0) {
            throw new IllegalArgumentException("Invalid student ID");
        }
        validate(s);
        return studentDao.update(s);
    }

    @Override
    public int delete(int id) {
        return studentDao.delete(id);
    }

    private void validate(Student s) throws IllegalArgumentException {
        if (s.getStudentNo() == null || s.getStudentNo().trim().isEmpty()) {
            throw new IllegalArgumentException("Student number is required");
        }
        if (s.getName() == null || s.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (s.getGender() == null || s.getGender().trim().isEmpty()) {
            throw new IllegalArgumentException("Gender is required");
        }
        if (s.getAge() == null || s.getAge() < 1 || s.getAge() > 150) {
            throw new IllegalArgumentException("Age must be between 1 and 150");
        }
    }
}

package com.example.student.dao;

import com.example.student.entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    @Override
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT id, student_no, name, gender, age, major, class_name, phone, email FROM students ORDER BY id DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rowToStudent(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs, ps, conn);
        }
        return list;
    }

    @Override
    public Student findById(int id) {
        String sql = "SELECT id, student_no, name, gender, age, major, class_name, phone, email FROM students WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rowToStudent(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs, ps, conn);
        }
        return null;
    }

    @Override
    public List<Student> search(String keyword) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT id, student_no, name, gender, age, major, class_name, phone, email FROM students "
                   + "WHERE name LIKE ? OR student_no LIKE ? ORDER BY id DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(sql);
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rowToStudent(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs, ps, conn);
        }
        return list;
    }

    @Override
    public int insert(Student s) {
        String sql = "INSERT INTO students (student_no, name, gender, age, major, class_name, phone, email) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, s.getStudentNo());
            ps.setString(2, s.getName());
            ps.setString(3, s.getGender());
            ps.setInt(4, s.getAge());
            ps.setString(5, s.getMajor());
            ps.setString(6, s.getClassName());
            ps.setString(7, s.getPhone());
            ps.setString(8, s.getEmail());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs, ps, conn);
        }
        return -1;
    }

    @Override
    public int update(Student s) {
        String sql = "UPDATE students SET student_no=?, name=?, gender=?, age=?, major=?, class_name=?, phone=?, email=? WHERE id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, s.getStudentNo());
            ps.setString(2, s.getName());
            ps.setString(3, s.getGender());
            ps.setInt(4, s.getAge());
            ps.setString(5, s.getMajor());
            ps.setString(6, s.getClassName());
            ps.setString(7, s.getPhone());
            ps.setString(8, s.getEmail());
            ps.setInt(9, s.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(ps, conn);
        }
        return 0;
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(ps, conn);
        }
        return 0;
    }

    private Student rowToStudent(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setStudentNo(rs.getString("student_no"));
        s.setName(rs.getString("name"));
        s.setGender(rs.getString("gender"));
        s.setAge(rs.getInt("age"));
        s.setMajor(rs.getString("major"));
        s.setClassName(rs.getString("class_name"));
        s.setPhone(rs.getString("phone"));
        s.setEmail(rs.getString("email"));
        return s;
    }
}

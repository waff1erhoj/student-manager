-- Run this script as MySQL root user to initialize the database
CREATE DATABASE IF NOT EXISTS student_manager
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE student_manager;

CREATE TABLE IF NOT EXISTS students (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    student_no  VARCHAR(20)  NOT NULL UNIQUE,
    name        VARCHAR(50)  NOT NULL,
    gender      VARCHAR(4)   NOT NULL,
    age         INT          NOT NULL,
    major       VARCHAR(100),
    class_name  VARCHAR(50),
    phone       VARCHAR(20),
    email       VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Sample data for testing
INSERT INTO students (student_no, name, gender, age, major, class_name, phone, email) VALUES
('2024001', '张三', '男', 20, '计算机科学与技术', '计科2101', '13800138001', 'zhangsan@example.com'),
('2024002', '李四', '女', 21, '软件工程', '软工2102', '13800138002', 'lisi@example.com'),
('2024003', '王五', '男', 19, '数据科学', '数据2101', '13800138003', 'wangwu@example.com');

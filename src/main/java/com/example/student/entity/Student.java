package com.example.student.entity;

public class Student {
    private Integer id;
    private String studentNo;
    private String name;
    private String gender;
    private Integer age;
    private String major;
    private String className;
    private String phone;
    private String email;

    public Student() {}

    public Student(Integer id, String studentNo, String name, String gender,
                   Integer age, String major, String className,
                   String phone, String email) {
        this.id = id;
        this.studentNo = studentNo;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.major = major;
        this.className = className;
        this.phone = phone;
        this.email = email;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", studentNo='" + studentNo + "', name='" + name + "'}";
    }
}

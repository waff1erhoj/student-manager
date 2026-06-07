package com.example.student.servlet;

import com.example.student.entity.Student;
import com.example.student.service.StudentService;
import com.example.student.service.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    private final StudentService studentService = new StudentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("search".equals(action)) {
            handleSearch(req, resp);
        } else if ("get".equals(action)) {
            handleGet(req, resp);
        } else {
            handleList(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            handleDelete(req, resp);
        } else if ("add".equals(action)) {
            handleAdd(req, resp);
        } else if ("edit".equals(action)) {
            handleEdit(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void handleList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Student> students = studentService.findAll();
        req.setAttribute("students", students);
        req.getRequestDispatcher("/static/index.jsp").forward(req, resp);
    }

    private void handleSearch(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String keyword = req.getParameter("keyword");
        List<Student> students = studentService.search(keyword);
        writeJson(resp, toJsonArray(students));
    }

    private void handleGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Student s = studentService.findById(id);
            if (s != null) {
                writeJson(resp, toJsonObject(s));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                writeJson(resp, "{\"error\":\"Student not found\"}");
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeJson(resp, "{\"error\":\"Invalid ID\"}");
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            int rows = studentService.delete(id);
            if (rows > 0) {
                writeJson(resp, "{\"success\":true}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                writeJson(resp, "{\"error\":\"Student not found\"}");
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeJson(resp, "{\"error\":\"Invalid ID\"}");
        }
    }

    private void handleAdd(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            Student s = parseForm(req);
            studentService.add(s);
            resp.sendRedirect(req.getContextPath() + "/students?action=list");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeJson(resp, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            Student s = parseForm(req);
            s.setId(Integer.parseInt(req.getParameter("id")));
            int rows = studentService.update(s);
            if (rows > 0) {
                resp.sendRedirect(req.getContextPath() + "/students?action=list");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                writeJson(resp, "{\"error\":\"Student not found\"}");
            }
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeJson(resp, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private Student parseForm(HttpServletRequest req) {
        Student s = new Student();
        s.setStudentNo(req.getParameter("studentNo"));
        s.setName(req.getParameter("name"));
        s.setGender(req.getParameter("gender"));
        s.setAge(Integer.parseInt(req.getParameter("age")));
        s.setMajor(req.getParameter("major"));
        s.setClassName(req.getParameter("className"));
        s.setPhone(req.getParameter("phone"));
        s.setEmail(req.getParameter("email"));
        return s;
    }

    private void writeJson(HttpServletResponse resp, String json) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

    private String toJsonArray(List<Student> students) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < students.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(toJsonObject(students.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }

    private String toJsonObject(Student s) {
        return String.format(
            "{\"id\":%d,\"studentNo\":\"%s\",\"name\":\"%s\",\"gender\":\"%s\","
            + "\"age\":%d,\"major\":\"%s\",\"className\":\"%s\","
            + "\"phone\":\"%s\",\"email\":\"%s\"}",
            s.getId(), esc(s.getStudentNo()), esc(s.getName()), esc(s.getGender()),
            s.getAge(), esc(s.getMajor()), esc(s.getClassName()),
            esc(s.getPhone()), esc(s.getEmail())
        );
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>学生管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<div class="container">
    <h1>学生管理系统</h1>

    <div class="toolbar">
        <input type="text" id="searchInput" placeholder="搜索姓名或学号..." autocomplete="off">
        <a href="${pageContext.request.contextPath}/static/add.jsp" class="btn btn-primary">添加学生</a>
    </div>

    <table id="studentTable">
        <thead>
            <tr>
                <th>ID</th>
                <th>学号</th>
                <th>姓名</th>
                <th>性别</th>
                <th>年龄</th>
                <th>专业</th>
                <th>班级</th>
                <th>电话</th>
                <th>邮箱</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${students}" var="s">
            <tr data-id="${s.id}">
                <td>${s.id}</td>
                <td>${s.studentNo}</td>
                <td>${s.name}</td>
                <td>${s.gender}</td>
                <td>${s.age}</td>
                <td>${s.major}</td>
                <td>${s.className}</td>
                <td>${s.phone}</td>
                <td>${s.email}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/static/edit.jsp?id=${s.id}" class="btn btn-sm">编辑</a>
                    <button class="btn btn-sm btn-danger" onclick="deleteStudent(${s.id}, this)">删除</button>
                </td>
            </tr>
            </c:forEach>
            <c:if test="${empty students}">
            <tr><td colspan="10" class="empty">暂无数据</td></tr>
            </c:if>
        </tbody>
    </table>
</div>

<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script>
var ctx = '${pageContext.request.contextPath}';

var searchTimer;
$('#searchInput').on('keyup', function() {
    clearTimeout(searchTimer);
    var keyword = $(this).val();
    searchTimer = setTimeout(function() {
        $.get(ctx + '/students', {action: 'search', keyword: keyword}, function(students) {
            var tbody = $('#studentTable tbody');
            tbody.empty();
            if (students.length === 0) {
                tbody.append('<tr><td colspan="10" class="empty">暂无数据</td></tr>');
                return;
            }
            $.each(students, function(i, s) {
                tbody.append(
                    '<tr data-id="' + s.id + '">'
                    + '<td>' + s.id + '</td>'
                    + '<td>' + s.studentNo + '</td>'
                    + '<td>' + s.name + '</td>'
                    + '<td>' + s.gender + '</td>'
                    + '<td>' + s.age + '</td>'
                    + '<td>' + (s.major || '') + '</td>'
                    + '<td>' + (s.className || '') + '</td>'
                    + '<td>' + (s.phone || '') + '</td>'
                    + '<td>' + (s.email || '') + '</td>'
                    + '<td><a href="' + ctx + '/static/edit.jsp?id=' + s.id + '" class="btn btn-sm">编辑</a> '
                    + '<button class="btn btn-sm btn-danger" onclick="deleteStudent(' + s.id + ', this)">删除</button></td>'
                    + '</tr>'
                );
            });
        });
    }, 300);
});

function deleteStudent(id, btn) {
    if (!confirm('确认删除该学生记录？')) return;
    $.post(ctx + '/students', {action: 'delete', id: id}, function(res) {
        if (res.success) {
            $(btn).closest('tr').fadeOut(300, function() { $(this).remove(); });
        } else {
            alert('删除失败');
        }
    }).fail(function() {
        alert('删除请求失败');
    });
}
</script>
</body>
</html>

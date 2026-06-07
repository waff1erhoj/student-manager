<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>编辑学生</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<div class="container">
    <h1>编辑学生</h1>
    <form id="editForm" method="post" action="${pageContext.request.contextPath}/students">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="id" id="studentId">
        <div class="form-group">
            <label>学号 <span class="required">*</span></label>
            <input type="text" name="studentNo" id="studentNo" required maxlength="20">
        </div>
        <div class="form-group">
            <label>姓名 <span class="required">*</span></label>
            <input type="text" name="name" id="name" required maxlength="50">
        </div>
        <div class="form-group">
            <label>性别 <span class="required">*</span></label>
            <select name="gender" id="gender" required>
                <option value="">请选择</option>
                <option value="男">男</option>
                <option value="女">女</option>
            </select>
        </div>
        <div class="form-group">
            <label>年龄 <span class="required">*</span></label>
            <input type="number" name="age" id="age" required min="1" max="150">
        </div>
        <div class="form-group">
            <label>专业</label>
            <input type="text" name="major" id="major" maxlength="100">
        </div>
        <div class="form-group">
            <label>班级</label>
            <input type="text" name="className" id="className" maxlength="50">
        </div>
        <div class="form-group">
            <label>电话</label>
            <input type="text" name="phone" id="phone" maxlength="20">
        </div>
        <div class="form-group">
            <label>邮箱</label>
            <input type="email" name="email" id="email" maxlength="100">
        </div>
        <div class="form-actions">
            <button type="submit" class="btn btn-primary">保存</button>
            <a href="${pageContext.request.contextPath}/students?action=list" class="btn">返回</a>
        </div>
    </form>
</div>

<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script>
var id = new URLSearchParams(window.location.search).get('id');
if (!id) {
    alert('缺少学生ID参数');
    window.location.href = '${pageContext.request.contextPath}/students?action=list';
}

$.get('${pageContext.request.contextPath}/students', {action: 'get', id: id}, function(s) {
    $('#studentId').val(s.id);
    $('#studentNo').val(s.studentNo);
    $('#name').val(s.name);
    $('#gender').val(s.gender);
    $('#age').val(s.age);
    $('#major').val(s.major);
    $('#className').val(s.className);
    $('#phone').val(s.phone);
    $('#email').val(s.email);
}).fail(function() {
    alert('加载学生信息失败');
    window.location.href = '${pageContext.request.contextPath}/students?action=list';
});

$('#editForm').on('submit', function(e) {
    var age = parseInt($('#age').val());
    if (isNaN(age) || age < 1 || age > 150) {
        alert('年龄必须在 1-150 之间');
        e.preventDefault();
    }
});
</script>
</body>
</html>

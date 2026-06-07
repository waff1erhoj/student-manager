# Student Manager - 学生管理系统

Java Web 学生信息管理系统，采用经典分层架构实现学生记录的增删改查。

## 技术栈

| 层级 | 技术 |
|------|------|
| 构建 | Maven (war) |
| 语言 | Java 8+ |
| Web 容器 | Tomcat 8.5 / Servlet 3.1 |
| 数据库 | MySQL 8.0+ |
| 前端 | JSP + JSTL + EL + jQuery 3.x + Ajax |
| 持久层 | JDBC + mysql-connector-java |

## 项目结构

```
src/main/
├── java/com/example/student/
│   ├── entity/Student.java              # 实体类
│   ├── dao/
│   │   ├── DbUtil.java                  # 数据库连接工具
│   │   ├── StudentDao.java              # DAO 接口
│   │   └── StudentDaoImpl.java          # DAO 实现
│   ├── service/
│   │   ├── StudentService.java          # 业务接口
│   │   └── StudentServiceImpl.java      # 业务实现（含验证）
│   └── servlet/
│       └── StudentServlet.java          # 控制器（单 Servlet 分发）
├── resources/db.properties              # 数据库配置
└── webapp/
    └── static/
        ├── index.jsp                    # 列表页（JSTL + EL + Ajax 搜索/删除）
        ├── add.jsp                      # 添加表单
        ├── edit.jsp                     # 编辑表单（Ajax 预填）
        ├── css/style.css
        └── js/jquery.min.js
```

## 一键克隆运行（从零开始）

以下步骤适用于任何安装了 Ubuntu 的电脑，从零搭建到运行只需几条命令。

### 环境准备

```bash
# 安装 Java、Maven、MySQL、Git
sudo apt-get update
sudo apt-get install -y default-jdk maven mysql-server mysql-client git

# 启动 MySQL
sudo service mysql start
```

### 克隆项目

```bash
git clone https://github.com/waff1erhoj/student-manager.git
cd student-manager
```

### 初始化数据库

```bash
# 创建数据库和表
sudo mysql < sql/init.sql

# 创建应用用户（JDBC 连接用）
sudo mysql -e "
CREATE USER IF NOT EXISTS 'student_app'@'localhost' IDENTIFIED BY 'student123';
GRANT ALL PRIVILEGES ON student_manager.* TO 'student_app'@'localhost';
FLUSH PRIVILEGES;
"
```

如果数据库密码不同，编辑 `src/main/resources/db.properties` 修改：

```properties
db.username=student_app
db.password=student123
```

### 启动运行

```bash
mvn clean package
mvn tomcat7:run
```

浏览器打开: **http://localhost:8080/student-manager/students?action=list**

### 一键脚本（可选）

首次运行也可以直接执行：

```bash
chmod +x setup.sh && ./setup.sh
```

## API 接口

| 方法 | URL | 说明 | 返回 |
|------|-----|------|------|
| GET | `/students?action=list` | 学生列表 | HTML |
| GET | `/students?action=search&keyword=X` | Ajax 搜索 | JSON |
| GET | `/students?action=get&id=X` | Ajax 单条查询 | JSON |
| POST | `/students?action=add` | 添加学生 | 重定向 |
| POST | `/students?action=edit&id=X` | 编辑学生 | 重定向 |
| POST | `/students?action=delete&id=X` | Ajax 删除 | JSON |

## 功能特性

- EL + JSTL 服务端渲染列表
- Ajax 异步搜索（防抖 300ms）
- Ajax 异步删除（渐隐动画 + 确认对话框）
- Ajax GET 预填编辑表单
- 服务端参数验证（必填项、年龄范围）
- UTF-8 全链路编码支持

# Student Manager - 学生管理系统

Java Web 学生信息管理系统，采用经典分层架构实现学生记录的增删改查。**跨平台，同时支持 Ubuntu 和 Windows。**

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

## Ubuntu 快速开始

### 1. 安装环境

```bash
sudo apt-get update
sudo apt-get install -y default-jdk maven mysql-server git
sudo service mysql start
```

### 2. 克隆项目

```bash
git clone https://github.com/waff1erhoj/student-manager.git
cd student-manager
```

### 3. 初始化数据库

```bash
sudo mysql < sql/init.sql
sudo mysql -e "CREATE USER IF NOT EXISTS 'student_app'@'localhost' IDENTIFIED BY 'student123'; GRANT ALL PRIVILEGES ON student_manager.* TO 'student_app'@'localhost'; FLUSH PRIVILEGES;"
```

### 4. 启动

```bash
mvn tomcat7:run
```

浏览器打开 **http://localhost:8080/student-manager/students?action=list**

或一键执行：`chmod +x setup.sh && ./setup.sh`

---

## Windows 快速开始

### 1. 安装环境

下载并安装以下软件（均提供 `.msi` 或 `.exe` 安装包）：

| 软件 | 下载地址 | 说明 |
|------|---------|------|
| JDK 17+ | https://adoptium.net | 安装时勾选"设置 JAVA_HOME" |
| Maven | https://maven.apache.org/download.cgi | 解压后将 `bin/` 加入 PATH |
| MySQL 8.0+ | https://dev.mysql.com/downloads/mysql | 选 MySQL Community Server，安装时记住 root 密码 |
| Git | https://git-scm.com/download/win | 默认选项安装即可 |

或使用 winget（Windows 10/11 自带）：

```powershell
winget install EclipseAdoptium.Temurin.17.JDK
winget install Apache.Maven.3
winget install Oracle.MySQL.8.0
winget install Git.Git
```

> **注意：** MySQL 安装完成后，确保 MySQL 服务已启动（任务管理器 → 服务 → MySQL80 状态为"正在运行"）。

### 2. 克隆项目

```powershell
git clone https://github.com/waff1erhoj/student-manager.git
cd student-manager
```

### 3. 初始化数据库

打开 **cmd** 或 **PowerShell**，切换到项目目录后：

```bash
# 用 root 登录 MySQL，输入安装时设置的 root 密码
mysql -u root -p < sql\init.sql

# 创建应用用户（同样需要输入密码）
mysql -u root -p -e "CREATE USER IF NOT EXISTS 'student_app'@'localhost' IDENTIFIED BY 'student123'; GRANT ALL PRIVILEGES ON student_manager.* TO 'student_app'@'localhost'; FLUSH PRIVILEGES;"
```

### 4. 启动

```bash
mvn tomcat7:run
```

浏览器打开 **http://localhost:8080/student-manager/students?action=list**

> 首次运行 Maven 会自动下载依赖，需等待 1-2 分钟。

也可以直接双击 `setup.bat` 脚本（需提前安装好 JDK、Maven、MySQL）。

---

## 数据库配置

如果 MySQL 用户名或密码与默认不同，编辑 `src/main/resources/db.properties`：

```properties
db.url=jdbc:mysql://localhost:3306/student_manager?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
db.username=你的用户名
db.password=你的密码
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

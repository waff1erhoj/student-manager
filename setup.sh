#!/bin/bash
# Student Manager - 一键环境搭建与启动
# 适用于 Ubuntu/Debian 系统

set -e

echo "==> 安装依赖..."
sudo apt-get update -qq
sudo apt-get install -y -qq default-jdk maven mysql-server mysql-client git 2>/dev/null

echo "==> 启动 MySQL..."
sudo service mysql start 2>/dev/null || true

echo "==> 初始化数据库..."
sudo mysql < sql/init.sql 2>/dev/null
sudo mysql -e "CREATE USER IF NOT EXISTS 'student_app'@'localhost' IDENTIFIED BY 'student123'; GRANT ALL PRIVILEGES ON student_manager.* TO 'student_app'@'localhost'; FLUSH PRIVILEGES;" 2>/dev/null

echo "==> 编译项目..."
mvn clean package -q

echo "==> 启动服务..."
echo "访问地址: http://localhost:8080/student-manager/students?action=list"
mvn org.apache.tomcat.maven:tomcat7-maven-plugin:2.2:run

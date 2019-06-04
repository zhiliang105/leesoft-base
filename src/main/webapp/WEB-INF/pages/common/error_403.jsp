<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="ls" uri="http://www.leesoft.cn/ls" %><!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@ include file="/WEB-INF/pages/common/_include_header.jsp" %>
    <title> 无访问权限 - 403 </title>
    <link href="<ls:path/>/static/plugins/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link href="<ls:path/>/static/plugins/animate/animate.min.css" rel="stylesheet">
    <link href="<ls:path/>/static/css/error.css" rel="stylesheet">
</head>
<body>
    <div class="box animated fadeInDown">
        <h1>403</h1>
        <h3>您没有访问权限！</h3>
        <div class="desc">
           	 抱歉，您暂时无权限访问哦~
        </div>
        <button type="submit" class="btn btn-primary" onclick="javascript:history.back();">返回</button>
    </div>
</body>
</html>

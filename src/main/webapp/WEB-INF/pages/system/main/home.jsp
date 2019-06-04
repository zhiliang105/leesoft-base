<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <%@ include file="/WEB-INF/pages/common/_include_header.jsp" %>
    <title><fmt:message key="sys.l.title"/>-首页</title>
    <link rel="stylesheet" href="<ls:path/>/static/plugins/jquery-plugins/jquery.mCustomScrollbar.min.css">
    <link rel="stylesheet" href="<ls:path/>/static/css/framework.css">
    <!--[if lt IE 9]>
      <script src="${pageContext.request.contextPath}/static/plugins/bootstrap/3.3.5/js/html5shiv.min.js"></script>
      <script src="${pageContext.request.contextPath}/static/plugins/bootstrap/3.3.5/js/respond.min.js"></script>
    <![endif]-->
  </head>
  <body class="mCustomScrollbar" data-mcs-theme="minimal-dark">
  	首页
	<script src="<ls:path/>/static/js/jquery-1.11.3.min.js"></script>
	<script src="<ls:path/>/static/plugins/jquery-plugins/jquery.mCustomScrollbar.concat.min.js"></script>
  </body>
</html>
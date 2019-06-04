<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="ls" uri="http://www.leesoft.cn/ls" %><!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <%@ include file="/WEB-INF/pages/common/_include_header.jsp" %>
    <title><fmt:message key="sys.l.title"/></title>
    <link rel="stylesheet" href="<ls:path/>/static/plugins/animate/animate.min.css">
    <link rel="stylesheet" href="<ls:path/>/static/css/login.css">
    <!--[if lt IE 9]>
      <script src="${pageContext.request.contextPath}/static/plugins/bootstrap/3.3.5/js/html5shiv.min.js"></script>
      <script src="${pageContext.request.contextPath}/static/plugins/bootstrap/3.3.5/js/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
	    if(window.parent != window){
			window.parent.location.reload(true);
		}
    </script>
  </head>
  <body>
  	<header class="t-navbar">
  		<div class="t-navbar-header">
  			<div class="logo"><a href=""><fmt:message key="sys.l.title"/></a></div>
  		</div>
	</header>
	<div class="login-main">
		<div class="login-box">
			<div class="login-box-header">系统登录</div>
			<div class="login-box-form">
				<form id="login-from" action='<ls:path/>/admin/login' method="post">
					<input type="hidden" id="publicKeyExponent" value="${publicKeyExponent }">
		            <input type="hidden" id="publicKeyModulus" value="${publicKeyModulus }">
					<div class="form-error"></div>
					<div class="form-item">
						<label class="form-label" for="userName">帐&nbsp;&nbsp;&nbsp;&nbsp;号:</label>
						<input class="form-input" type="text" id="userName" name="userName" value="${userName }" autocomplete="off" placeholder="请输入您的登录账号">
					</div>
					<div class="form-item">
						<label class="form-label" for="password">密&nbsp;&nbsp;&nbsp;&nbsp;码:</label>
						<input class="form-input" type="password" id="password" name="password" value="${password }" autocomplete="off" placeholder="请输入您的登录密码">
					</div>
					<div class="form-item">
						<label class="form-label" for="validCode">验证码:</label>
						<input class="form-input" type="text" id="validCode" name="validCode" style="width: 252px;" value="${validCode }" autocomplete="off" placeholder="请输入验证码">
						<img id="validCodeImg" class="form-valid" src="<ls:path/>/validCode" title="点击刷新" onclick="resetRandCodeImage()">
					</div>
					<div class="form-item" style="text-align: center; padding-top: 20px;">
						<button id="login-btn" type="button" class="btn btn-lg btn-primary" style="width: 200px;">登&nbsp;&nbsp;&nbsp;&nbsp;录</button>
						<div id="loging">
							<div class="object" id="object_1"></div>
							<div class="object" id="object_2"></div>
							<div class="object" id="object_3"></div>
							<div class="object" id="object_4"></div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<footer>
		<div class="copyright">
			Copyright © 2017-${year } 南通力软网络科技有限公司 All Rights Reserved.
		</div>
	</footer>
	
	<script src="<ls:path/>/static/js/jquery-1.11.3.min.js"></script>
    <script src="<ls:path/>/static/js/jquery.backstretch.min.js"></script>
    <script src="<ls:path/>/static/js/security.js"></script>
    <script type="text/javascript">
    	$(function(){
    		$.backstretch(["<ls:path/>/static/images/bg.gif"], {duration: 3000, fade: 200});  
    		$(".login-box").addClass("animated fadeInLeft").one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
    		      $(this).removeClass("animated fadeInLeft");
    		});
    		$("#login-btn").click(function(){
    			submit();
    		});
    		$("#userName,#password,#validCode").keypress(function(event){
    			if(event.keyCode == 13) submit();
    		});
    		$(document).ajaxStop(function() {
    			loginEnd();
    		});
    	});
    	
    	function submit() {
    		var userName = $("#userName").val();
			if(userName == "") {
				$("#userName").focus();
				return error("请输入账户名")
			}
			var password = $("#password").val();
			if(password == "") {
				$("#password").focus();
				return error("请输入账户密码");
			}
			var validCode = $("#validCode").val();
			if(validCode == "") {
				$("#validCode").focus();
				return error("请输入验证码");
			}
			var key = new RSAUtils.getKeyPair($("#publicKeyExponent").val(), "", $("#publicKeyModulus").val());
    		var reversedPwd = password.split("").reverse().join("");
    		var encrypedPwd = RSAUtils.encryptedString(key, reversedPwd);  
    		$.ajax({
				url :  $("#login-from").attr("action"),
				type : "POST",
				cache : false,
				data : {userName : userName,password : encrypedPwd, validCode : validCode},
				dataType : "json",
				beforeSend : function(XMLHttpRequest) {
					loginStart();
				},
				success : function(rs) {
					var code = rs.code;
					if(code == 0) {
						error(rs.msg);
						resetRandCodeImage();
					} else if(code == 1) {
						error("登录成功,进入首页...");
						window.location.href = "<ls:path />/admin/index";
					}
				},
				error : function() {
					error("登录失败,系统错误");
					resetRandCodeImage();
				}
			});
    	}
    	
    	function loginStart() {
    		$("#login-btn").hide();
    		$("#loging").show();
    		error("正在登录...")
    	}
    	
    	function loginEnd() {
    		$("#login-btn").show();
    		$("#loging").hide();
    	}
    	
    	function resetRandCodeImage() {
	    	var img = $("#validCodeImg");
	    	var src = img.attr("src");
	    	var index = src.indexOf("?");
	    	if(index > 0) {
	    		src = src.substring(0,index);
	    	}
	    	img.attr("src",src + "?t=" + new Date().getTime());
	    }
    	
    	function error(msg) {
    		$(".form-error").html(msg);
    		return false;
    	}
    </script>
  </body>
</html>
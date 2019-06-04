<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<%@ include file="/WEB-INF/pages/common/_include_header.jsp" %>
	    <title><fmt:message key="sys.l.title"/></title>
	    <link rel="stylesheet" href="<ls:path/>/static/plugins/bootstrap/3.3.5/css/bootstrap.min.css">
	    <link rel="stylesheet" href="<ls:path/>/static/plugins/font-awesome/4.7.0/css/font-awesome.min.css">
	    <link rel="stylesheet" href="<ls:path/>/static/plugins/jquery-plugins/jquery.mCustomScrollbar.min.css">
	    <link rel="stylesheet" href="<ls:path/>/static/plugins/jquery-plugins/metisMenu.min.css">
	    <link rel="stylesheet" href="<ls:path/>/static/plugins/animate/animate.min.css">
	    <link rel="stylesheet" href="<ls:path/>/static/plugins/toast/css/toast.style.css">
	    <link rel="stylesheet" href="<ls:path/>/static/css/plugin-extend.css">
	    <link rel="stylesheet" href="<ls:path/>/static/css/framework.css">
	    <link rel="stylesheet" href="<ls:path/>/static/css/index.css">
	    <!--[if lt IE 9]>
	      <script src="${pageContext.request.contextPath}/static/plugins/bootstrap/3.3.5/js/html5shiv.min.js"></script>
          <script src="${pageContext.request.contextPath}/static/plugins/bootstrap/3.3.5/js/respond.min.js"></script>
	    <![endif]-->
	    <script type="text/javascript">
	    	var ctx = "<ls:path/>";
	    </script>
	</head>
	<body>
		<div class="ls-layout-header"><div class="ls-title"><fmt:message key="sys.l.title"/></div></div>
		<div class="ls-header-menu">
			<div class="ls-header-menu-item">
				<a href="javascript:toHomePage()" class="ls-header-menu-btn"><i class="fa fa-home"></i><span>首页</span></a>
			</div>
			<div class="ls-header-menu-item item-dropdown">
				<a href="javascript:void(0)" class="ls-header-menu-btn"><i class="fa fa-user-circle-o"></i><span>超级管理员</span></a>
				<div class="dropdown-info">
					<div class="user-info">
						<div class="user-info-photo">
							<img src='<ls:path/>/static/images/logo.jpg'>
						</div>
						<div class="divider"></div>
						<div class="user-info-list">
							<a href="javascript:;"><span class="user-info-icon"><i class="fa fa-user-circle"></i></span>个人信息</a>
							<a href="javascript:;"><span class="user-info-icon"><i class="fa fa-pencil-square-o"></i></span>修改密码</a>
							<a href="javascript:;"><span class="user-info-icon"><i class="fa fa-file-image-o"></i></span>头像设置</a>
							<a href="javascript:;"><span class="user-info-icon"><i class="fa fa-list"></i></span>系统日志</a>
							<a href="javascript:;"><span class="user-info-icon"><i class="fa fa-calendar"></i></span>我的备忘</a>
						</div>
						<div class="divider"></div>
						<div class="user-info-item">
							<div class="item"><span>用户账户 :</span> superadmin</div>
							<div class="item"><span>所在公司 :</span> 力软网络科技有限公司</div>
							<div class="item"><span>所在部门 :</span> 营销一部</div>
							<div class="item"><span>登录时间 :</span> 2017-12-01 12:00:00</div>
						</div>
						<div class="divider"></div>
						<div class="user-info-logout">
							<a href="javascript:logout();"><i class="fa fa-sign-out"></i>注销登录</a>
						</div>
					</div>
				</div>
			</div>
			<div class="ls-header-menu-item item-dropdown">
				<a href="javascript:void(0)" class="ls-header-menu-btn"><i class="fa fa-cog"></i><span>系统设置</span></a>
				<div class="dropdown-info">
					<ul>
						<li><a href="javascript:;"><i class="fa fa-caret-square-o-right"></i>简体中文</a></li>
						<li><a href="javascript:;"><i class="fa fa-caret-square-o-right"></i>English</a></li>
						<li class="divider"></li>
						<li><a href="javascript:;"><i class="fa fa-telegram"></i>系统风格</a></li>
						<li><a href="javascript:;"><i class="fa fa-eraser"></i>清除缓存</a></li>
						<li class="divider"></li>
						<li><a href="javascript:logout();"><i class="fa fa-sign-out"></i>注销登录</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="ls-layout-left">
			<div class="ls-layout-left-sidebar"><i class="fa fa-outdent"></i></div>
			<div class="ls-layout-left-menu mCustomScrollbar" data-mcs-theme="minimal-dark" data-mcs-axis="y">
				<nav class="navbar navbar-static-top">
				    <div class="sidebar" role="navigation">
			           <ul class="nav metismenu" id="side-menu">
			                <li>
			                    <a class="menu-first" href="javascript:;"><i class="fa fa-sitemap fa-fw"></i><span class="menu-text">系统管理</span><span class="fa arrow"></span></a>
			                    <ul class="nav">
			                        <li><a id="menu-id-1" class="menu-second" href="javascript:;" data-url="<ls:path/>/admin/home"><i class="fa fa-flag"></i><span class="menu-text">数据字典</span></a></li>
			                        <li><a id="menu-id-2" class="menu-second" href="javascript:;" data-url="<ls:path/>/admin/home"><i class="fa fa-flag"></i><span class="menu-text">机构管理</span></a></li>
			                        <li><a id="menu-id-3" class="menu-second" href="javascript:;" data-url="<ls:path/>/admin/home"><i class="fa fa-flag"></i><span class="menu-text">岗位管理</span></a></li>
			                        <li><a id="menu-id-4" class="menu-second" href="javascript:;" data-url="<ls:path/>/admin/system/role/list"><i class="fa fa-flag"></i><span class="menu-text">角色管理</span></a></li>
			                        <li><a id="menu-id-5" class="menu-second" href="javascript:;" data-url="<ls:path/>/admin/home"><i class="fa fa-flag"></i><span class="menu-text">用户管理</span></a></li>
			                        <li>
			                            <a class="menu-second" href="javascript:;"><i class="fa fa-eyedropper"></i><span class="menu-text">三级菜单管理</span><span class="fa arrow"></span></a>
			                            <ul class="nav">
			                                <li><a id="menu-id-6" class="menu-third" href="javascript:;" data-url="<ls:path/>/admin/home"><i class="fa fa-flag"></i><span class="menu-text">三级菜单子菜单</span></a></li>
			                                <li><a id="menu-id-7" class="menu-third" href="javascript:;" data-url="<ls:path/>/admin/home"><i class="fa fa-flag"></i><span class="menu-text">三级菜单子菜单</span></a></li>
			                                <li><a id="menu-id-8" class="menu-third" href="javascript:;" data-url="<ls:path/>/admin/home"><i class="fa fa-flag"></i><span class="menu-text">三级菜单子菜单2</span></a></li>
			                            </ul>
			                        </li>
			                        <li>
			                            <a class="menu-second" href="javascript:;"><i class="fa fa-eyedropper"></i><span class="menu-text">三级菜单管理</span><span class="fa arrow"></span></a>
			                            <ul class="nav">
			                                <li><a id="menu-id-9" class="menu-third" href="javascript:;" data-url="<ls:path/>/admin/home"><i class="fa fa-flag"></i><span class="menu-text">三级菜单子菜单</span></a></li>
			                                <li><a id="menu-id-10" class="menu-third" href="javascript:;" data-url="<ls:path/>/admin/home"><i class="fa fa-flag"></i><span class="menu-text">三级菜单子菜单</span></a></li>
			                                <li><a id="menu-id-11" class="menu-third" href="javascript:;" data-url="<ls:path/>/admin/home"><i class="fa fa-flag"></i><span class="menu-text">三级菜单子菜单2</span></a></li>
			                            </ul>
			                        </li>
			                    </ul>
			                </li>
			            </ul>
				    </div>
				</nav>
			</div>
		</div>
		<div class="ls-layout-center">
			<div class="ls-tabs">
				<a href="javascript:;" class="tabs-roll roll-left"><i class="fa fa-backward"></i></a>
				<a href="javascript:;" class="tabs-roll roll-right"><i class="fa fa-forward"></i></a>
				<a href="javascript:;" class="tabs-roll tab-dropdown"><i class="fa fa-chevron-down"></i></a>
				<div class="tabs-item-container">
					<ul class="nav nav-tabs">
						<li id="li-ls-home" class="active" data-closable="false"><a href="#tab-ls-home" data-toggle="tab"><i class="fa fa-home"></i>首页</a></li>
					</ul>
				</div>
			</div>
			<div class="tab-content">
				<div class="tab-pane fade in active" id="tab-ls-home"><iframe id="frame-ls-home" frameborder="0" src='<ls:path/>/admin/home'></iframe></div>
			</div>
		</div>
		<div class="ls-layout-bottom">
			Copyright © 2017-${year } 南通力软网络科技有限公司 All Rights Reserved.
		</div>
		<div class="ls-page-loading">
			<div class="loading-container">
				<div class="loader"><div class="loader-outter"></div><div class="loader-inner"></div></div>
				<div class="loader-text">页面加载中,请稍后...</div>
			</div>
		</div>
	<script src="<ls:path/>/static/js/jquery-1.11.3.min.js"></script>
	<script src="<ls:path/>/static/plugins/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src="<ls:path/>/static/plugins/jquery-plugins/jquery.mCustomScrollbar.concat.min.js"></script>
	<script src="<ls:path/>/static/plugins/jquery-plugins/metisMenu.min.js"></script>
	<script src="<ls:path/>/static/plugins/layui/layer/3.1.0/layer.js"></script>
	<script src="<ls:path/>/static/plugins/toast/js/toast.script.js"></script>
	<script src="<ls:path/>/static/js/locale/framework-<ls:locale/>.js"></script>
	<script src="<ls:path/>/static/js/common/index-tab.js"></script>
	<script src="<ls:path/>/static/js/common/index.js"></script>
	<script src="<ls:path/>/static/js/common/framework-dialog.js"></script>
	</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<%@ include file="/WEB-INF/pages/common/_include_header.jsp"%>
		<title>角色管理</title>
		<%@ include file="/WEB-INF/pages/common/_include_static.jsp"%>
	</head>
	<body>
	
		<ls:layoutContainer>
		
			<!--  
			<ls:layoutLeft title="左侧顶部标题">
				这里是左侧内容区域,可以方式树形结构,列表内容等
			</ls:layoutLeft>
			<ls:layoutTop>
				在这里可以放置一个layui表单<br>
				然后设置一些查询条件
			</ls:layoutTop>
			-->
			
			<ls:layoutCenter>
				<table id="data_list">
					<thead>
						 <tr>
						 	<th data-width="3%" data-checkbox="true"></th>
						 	<th data-width="10%" data-field="code" data-align="center" data-sortable="true">角色编号</th>
						 	<th data-width="10%" data-field="name" data-align="center" data-sortable="true">角色名称</th>
						 	<th data-width="5%" data-field="isSystem" data-align="center" data-sortable="true">系统内置</th>
						 	<th data-width="5%" data-field="sort" data-align="center" data-sortable="true">排序号</th>
						 	<th data-width="10%" data-field="type" data-align="center" data-sortable="true">角色类型</th>
						 	<th data-width="10%" data-field="createName" data-align="center" data-sortable="true">创建人员</th>
						 	<th data-width="15%" data-field="createTime" data-align="center" data-sortable="true">创建时间</th>
						 	<th data-width="20%" data-field="remark" data-align="left">备注信息</th>
						 </tr>
					</thead>
			    </table>
			    <div id="toolbar">
			    	<div class="btn-group">
				    	<button type="button" class="btn btn-default" onclick="LS.openFormDialog({title:'新增角色', tableId:'data_list', url:'<ls:path/>/admin/system/role/toAddFrom', width: '750px', height: '430px'})"><i class="fa fa-plus"></i> 新增 </button>
				    	<button type="button" class="btn btn-default" onclick="LS.updateTableData({title:'修改角色信息', tableId:'data_list', url:'<ls:path/>/admin/system/role/toUpdateFrom', width: '750px', height: '430px'})"><i class="fa fa-edit"></i> 修改 </button>
				    	<button type="button" class="btn btn-default" onclick="LS.deleteTableData({tableId:'data_list',url:'<ls:path/>/admin/system/role/delete'})"><i class="fa fa-trash-o"></i> 删除 </button>
		            	<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><i class="fa fa-inbox"></i> 角色授权 <span class="caret"></span> </button>
					    <ul class="dropdown-menu" role="menu">
					        <li> <a href="javascript:;"><i class="fa fa-flag"></i> 功能授权</a> </li>
					        <li> <a href="javascript:;"><i class="fa fa-flag"></i> 数据授权</a> </li>
					    </ul>
		            </div>
		        </div>
		    </ls:layoutCenter>
		</ls:layoutContainer>
	
		<!--  
		<div class="bt-container">
			<div class="bt-layout bt-layout-left">
				<div class="panel panel-default">
					 <div class="panel-heading"> <h3 class="panel-title"> 左侧顶部标题 </h3> </div>
					 <div class="panel-body tree-fluid mCustomScrollbar" data-mcs-theme="minimal-dark">
					 	这里是左侧内容区域,可以方式树形结构,列表内容等
				     </div>
				</div>
			</div>
			<div class="bt-layout bt-layout-top">
				<div class="layui-fluid form-fluid">
					在这里可以放置一个layui表单<br>
					然后设置一些查询条件
				</div>
			</div>
			<div class="bt-layout bt-layout-center">
				<table id="data_list">
					<thead>
						 <tr>
						 	<th data-width="3%" data-checkbox="true"></th>
						 	<th data-width="10%" data-field="code" data-align="center" data-sortable="true">角色编号</th>
						 	<th data-width="10%" data-field="name" data-align="center" data-sortable="true">角色名称</th>
						 	<th data-width="5%" data-field="isSystem" data-align="center" data-sortable="true">系统内置</th>
						 	<th data-width="5%" data-field="sort" data-align="center" data-sortable="true">排序号</th>
						 	<th data-width="10%" data-field="type" data-align="center" data-sortable="true">角色类型</th>
						 	<th data-width="10%" data-field="createName" data-align="center" data-sortable="true">创建人员</th>
						 	<th data-width="15%" data-field="createTime" data-align="center" data-sortable="true">创建时间</th>
						 	<th data-width="20%" data-field="remark" data-align="left">备注信息</th>
						 </tr>
					</thead>
			    </table>
			    <div id="toolbar">
			    	<div class="btn-group">
				    	<button type="button" class="btn btn-default" onclick="LS.openFormDialog({title:'新增角色', tableId:'data_list', url:'<ls:path/>/admin/system/role/toAddFrom', width: '750px', height: '430px'})"><i class="fa fa-plus"></i> 新增 </button>
				    	<button type="button" class="btn btn-default" onclick="LS.updateTableData({title:'修改角色信息', tableId:'data_list', url:'<ls:path/>/admin/system/role/toUpdateFrom', width: '750px', height: '430px'})"><i class="fa fa-edit"></i> 修改 </button>
				    	<button type="button" class="btn btn-default" onclick="LS.deleteTableData({tableId:'data_list',url:'<ls:path/>/admin/system/role/delete'})"><i class="fa fa-trash-o"></i> 删除 </button>
		            	<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><i class="fa fa-inbox"></i> 角色授权 <span class="caret"></span> </button>
					    <ul class="dropdown-menu" role="menu">
					        <li> <a href="javascript:;"><i class="fa fa-flag"></i> 功能授权</a> </li>
					        <li> <a href="javascript:;"><i class="fa fa-flag"></i> 数据授权</a> </li>
					    </ul>
		            </div>
		        </div>
			</div>
		</div>
		-->
		
	    <script type="text/javascript">
	    	$(function(){
	    		LS.bootstrapTable("data_list",{
	    			url : ctx + "/admin/system/role/list",
	    			toolbar : "#toolbar",
	    			sortName : "sort"
	    		});
	    	});
	    	function doRefresh() {
	    		$("#data_list").bootstrapTable("refresh");
	    	}
	    </script>
	</body>
</html>
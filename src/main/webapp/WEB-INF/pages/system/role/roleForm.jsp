<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<%@ include file="/WEB-INF/pages/common/_include_header.jsp"%>
		<title>角色管理-表单</title>
		<%@ include file="/WEB-INF/pages/common/_include_static.jsp"%>
	</head>
	<body class="mCustomScrollbar" data-mcs-theme="minimal-dark">
		<div class="layui-fluid">
			<form id="form" class="layui-form layui-form-pane" lay-filter="form" action="<ls:path/><c:if test="${role!=null }">/admin/system/role/update</c:if><c:if test="${role==null }">/admin/system/role/save</c:if>">
				<input type="hidden" id="id" name="id" value="${role.id }">
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">角色编码</label>
					    <div class="layui-input-inline">
					        <input type="text" name="code" id="code" lay-verify="required|valid" lay-verType='tips' lay-oldValue="${role.code}" lay-validUrl="<ls:path/>/admin/system/role/validate" autocomplete="off" class="layui-input" placeholder="请输入角色编码" value="${role.code}">
					    </div>
				    </div>
					<div class="layui-inline">
						<label class="layui-form-label">角色名称</label>
					    <div class="layui-input-inline">
					        <input type="text" name="name" id="name" lay-verify="required" lay-verType='tips' autocomplete="off" class="layui-input" placeholder="请输入角色名称" value="${role.name}">
					    </div>
				    </div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">角色类型</label>
					    <div class="layui-input-inline">
					    	  <select name="type" id="type">
						        	<option value="">请选择角色类型</option>
						        	<option value="系统角色">系统角色</option>
						      </select>
					    </div>
				    </div>
					<div class="layui-inline">
						<label class="layui-form-label">角色序号</label>
					    <div class="layui-input-inline">
					        <input type="number" name="sort" id="sort" lay-verify="required" lay-verType='tips' autocomplete="off" class="layui-input"  value="${role.sort}" placeholder="请输入角色序号">
					    </div>
				    </div>
				</div>
				<div class="layui-form-item" pane="">
					<label class="layui-form-label">内置角色</label>
				    <div class="layui-input-block">
				    	<input type="checkbox" checked="" name="isSystem" id="isSystem" lay-skin="switch" lay-filter="switchTest" lay-text="是|否" value="true">
				    </div>
				</div>
				<div class="layui-form-item layui-form-text">
				    <label class="layui-form-label">备注信息</label>
				    <div class="layui-input-block">
				      <textarea name="remark" id="remark" placeholder="" class="layui-textarea">${role.remark}</textarea>
				    </div>
				 </div>
				<button lay-submit lay-filter="doSubmit" id="btn-submit" style="display: none;">提交</button>     
			</form>
		</div>
	    <script type="text/javascript">
	    LS.initLayForm({
	    	initFun : function(layo){
	    		layo.form.val('form', {
		    	    "type": "${role.type}",
		    	    "isSystem": "${role.isSystem}" == "true" ? true : false
		    	});
	    	}
	    });
	    function submit() {
	    	$("#btn-submit").click();
	    }
	    </script>
	</body>
</html>
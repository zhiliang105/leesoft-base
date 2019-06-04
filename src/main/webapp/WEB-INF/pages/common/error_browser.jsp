<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@taglib prefix="ls" uri="http://www.leesoft.cn/ls" %><!DOCTYPE html>
<html lang="zh-CN">
  <head>
   	<meta charset="utf-8">
    <title>温馨提醒:您的浏览器需要更新才能访问哦 ( ^_^ )</title>
    <%@ include file="/WEB-INF/pages/common/_include_header.jsp" %>
  	<link href="<ls:path />/static/css/error.css" rel="stylesheet" type="text/css">
  </head>
  <body>
  	<div id='browser'>
        <div class='b-title'>
            <strong>使用一款优质浏览器，离科技更近一步</strong>
        </div>
        <p>您正在使用的浏览器内核版本过低，除了有可能遭受病毒与恶意软件的侵袭之外，也无法体验到最新互联网技术带来的优质显示与交互效果。为确保您能够体验到最佳的浏览效果，我们推荐您选择使用下列最新的优质浏览器：</p>
        <div class='downloading'>
            <a href='http://www.firefox.com.cn/download/' target='_blank'>
                <img src='<ls:path />/static/images/browser/firefox.png' />
            </a>
            <a href='http://wirrorcdn.qiniudn.com/software/browser/ChromeStandaloneSetup.Win.38.0.2125.111.exe' target='_blank'>
                <img src='<ls:path />/static/images/browser/chrome.png' />
            </a>
            <a href='http://windows.microsoft.com/zh-cn/internet-explorer/ie-11-worldwide-languages/' target='_blank'>
                <img src='<ls:path />/static/images/browser/ie.png' />
            </a>
            <a href='http://support.apple.com/kb/DL1531' target='_blank'>
                <img src='<ls:path />/static/images/browser/safari.png' />
            </a>
        </div>
        <div class="bottom">或使用其他高版本内核的浏览器,<strong>给您带来的不便,敬请谅解!</strong></div>
    </div>
  </body>
</html>
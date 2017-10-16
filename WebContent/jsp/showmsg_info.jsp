<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>查看反馈信息</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/common.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/register.css" />
<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<style type="text/css">

.regTop{
    width: 100%;
    padding:8% 0 6%;
    background: #50b4f9;
    text-align: center;
    color: #ffffff;
    position: relative;
}
</style>
</head>
<body style="font-size: 60px;">
	 <div class="register">
		<div class="regTop">
			<span>查看反馈信息</span>
		</div>
		<div class="content" style="margin: 35px;">
			<form action="${pageContext.request.contextPath }/work/addwork"
				method="post">
				<div class="message">
				<c:forEach items="${techlist}" var="item" varStatus="a">
					<table style="width: 100%;table-layout: fixed; border-spacing:0px 10px; font-size: 40px; margin-bottom: 40px; margin-top: 35px; ">
					<tr>
							<td style="width: 30%; "><input type="text" style="display: none;" value="${openid}"
								name="openid" /></td>
							<td></td>
						</tr>
						<tr>
							<td style="width: 30%;"><label><strong>设备名称：</strong></label></td>
							<td><strong>${item.techsupportDevicename }</strong></td>
							
						</tr>
						<tr>
							<td><label>业务类型：</label></td>
							<td>${item.type }</td>
						</tr>
						<tr>
							<td><label>提交时间：</label></td>
							<td>${item.techsupportUptime }</td>
						</tr>
						<tr>
							<td><label>具体描述：</label></td>
							<td  style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis;">${item.techsupportDescribe }</td>
						</tr>
						<tr>
							<td><label>当前状态：</label></td>
							<td>${tsStatus[a.index]}</td>
						</tr>
						<tr>
							<td style="vertical-align:top;"><label>反馈信息：</label></td>
							<c:if test="${!empty item.techsupportFeedback}">
								<td style="width:70%; word-wrap:break-word;word-break:break-all;">
								${item.techsupportFeedback }</td>
							</c:if>
							<c:if test="${empty item.techsupportFeedback}">
								<td style="width:70%; word-wrap:break-word;word-break:break-all;">
								暂无反馈信息，请耐心等待</td>
							</c:if>
						</tr>
						<c:if test="${!empty tsManagerList[a.index]}">
							<tr>
								<td><label>处理人：</label></td>
								<td>${tsManagerList[a.index].managerName }</td>
							</tr>
							<tr>
								<td><label>联系方式：</label></td>
								<td><a href="tel:${tsManagerList[a.index].managerTelephone }">${tsManagerList[a.index].managerTelephone }</a></td>
							</tr>
						</c:if>
					</table>
					<hr style=" height:2px;border:none;border-top:2px dotted #185598;">
				</c:forEach>
				
				</div>
			</form>
		</div>
	</div> 
</body>
</html>
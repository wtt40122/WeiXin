<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.wt.po.SNSUserInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Oauth2.0网页授权</title>
<meta content="viewport" content="width=device-width;user-scalable=0">
</head>
<body>
	<%
		SNSUserInfo user = (SNSUserInfo)request.getAttribute("snsUserInfo");
		if(null != user){
	%>
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr><td width="20%">属性</td><td width="80%">值</td></tr>
		<tr><td>OpenId</td><td><%=user.getOpenId() %></td></tr>
		<tr><td>昵称</td><td><%=user.getNickname() %></td></tr>
		<tr><td>性别</td><td><%=user.getSex() %></td></tr>
		<tr><td>国家</td><td><%=user.getCountry() %></td></tr>
		<tr><td>省份</td><td><%=user.getProvince() %></td></tr>
		<tr><td>城市</td><td><%=user.getCity() %></td></tr>
		<tr><td>头像</td><td><%=user.getHeadImgUrl() %></td></tr>
		<tr><td>特权</td><td><%=user.getPrivilegeList() %></td></tr>
	</table>
	<%
		}else{
			out.print("未获取到用户信息");
		}
	%>
</body>
</html>
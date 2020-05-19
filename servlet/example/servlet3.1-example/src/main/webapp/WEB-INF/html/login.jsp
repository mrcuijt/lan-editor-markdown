<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Verify</title>
</head>
<body>

	<!-- 引入公共的页头 -->
	<%-- <jsp:include page="../loh/comm/header.jsp"></jsp:include> --%>

	<div class="container">
		<div class="row">
			<div class="login-div">
				<form name="loginForm" class="login-form" method="post"
					action="<%=request.getContextPath()%>/verify.do">
					<table>
						<tr>
							<td>
								<label for="username">用户名：</label> 
								<input name="userName" type="text" placeholder="请输入用户名"  value="张三"/>
							</td>
						</tr>
						<tr>
							<td>
								<label for="password">密码：</label> 
								<input name="password" type="password" placeholder="请输入密码" value=""/>
							</td>
						</tr>
						<tr>
							<td>
								<label for="verifyCode">验证码：</label> 
								<img src="<%=request.getContextPath()%>/verifyCode.do?t=<%=System.currentTimeMillis()%>" />
								<input name="verifyCode" type="text" placeholder="请输入验证码" value="" />
							</td>
						</tr>
						<tr>
							<td>
								<a role="button" class="btn btn-default" href="<%=request.getContextPath()%>/register.do">
									<!-- <button class="btn btn-default" type="button">注册</button> -->
									注册
								</a>
								<button class="btn btn-default" type="submit">登录</button><br/>
								<span>${message }</span>
								<span>${sessionScope.message }</span>
								<%
									String message = request.getParameter("message");
								%>
								<span>message:<%=message%></span>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

	<!-- 引入公共的页脚 -->
	<%-- <jsp:include page="../loh/comm/footer.jsp"></jsp:include> --%>
</body>
</html>
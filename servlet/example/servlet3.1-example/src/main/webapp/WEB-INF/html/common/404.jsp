<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>

<body>
<center>
    <table width=400px cellpadding="5">
        <tr>
            <td align="center">
                <img src="<%=path%>/images/common/404/404.gif"/>
                <br>
                <hr style: dotted color="#989898"/>
                <p style="display:none">
                    没有找到页面。。。
                </p>
            </td>
        </tr>
    </table>
</center>
</body>
</html>

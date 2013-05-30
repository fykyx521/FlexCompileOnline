<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="flex2.tools.oem.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>错误报表</title>
</head>
<body>
	<%
		
		Message[] msgs=(Message[])request.getAttribute("error");
		for(Message msg:msgs)
		{
			 response.getOutputStream().write(("<div>"+msg.getLine()+":"+msg.getLevel()+":"+msg.getPath()+":"+msg.getColumn()+"</div>").getBytes());
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
	%>
</body>
</html>
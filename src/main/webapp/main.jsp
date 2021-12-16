<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main Page :: Dynamic :: AccessInfo</title>
</head>
<body>
	<h1>${accessInfo}</h1>
	<form action="AccessOut" method="post"> 
		<input type="submit" value="로그아웃"/>
		<input type="hidden" name="seCode" value="${seCode}"/>
		<input type="hidden" name="emCode" value="${emCode}"/>
	</form>
</body>
</html>
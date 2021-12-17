<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main Page :: Dynamic :: AccessInfo</title>
</head>
<body>
	<h1>${accessInfo[0].sename}(${accessInfo[0].secode})</h1>
	<h1>${accessInfo[0].emName}(${accessInfo[0].emcode})</h1>
	<h1>최근 접속 일자 ${accessInfo[0].date}</h1>
	<form action="AccessOut" method="post"> 
		<input type="submit" value="로그아웃"/>
		<input type="hidden" name="seCode" value="${accessInfo[0].secode}"/>
		<input type="hidden" name="emCode" value="${accessInfo[0].emcode}"/>
	</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#info {height:25px; line-height:25px;
		  background-color: #81BA7B; border:2px solid #81BA7B;
		  color: #3A3A3A; font-size:10pt;
		  text-align:right;}
	#index {width:15%; height:570px; float:left;background-color: #81BA7B;border:0px solid #81BA7B;}	
	#data {width:85%;height:570px;float:right;background-color: #EAEAEA;border:0px solid #EAEAEA;}
	#footer {clear:both;position:absolute; top:93%;
			width: 98.463%; height: 25px; line-height: 30px;
		  background-color: #81BA7B; border:2px solid #81BA7B;
		  color: #3A3A3A; font-size:10pt;
		  text-align:right;}  
</style>
</head>
<body>
	<div id="info" >
		${seName}(${seCode})
		<span>${emName}(${emCode})</span>
		최근 접속 일자 ${date}
		<span><input type="button" class="btn" onClick="accessOut('${accessInfo[0].secode}','${accessInfo[0].emcode}')" value="로그아웃"/></span>		
	</div>
	<div id="index"></div>
	<div id="data"></div>
	<div id="footer"></div>
</body>
</html>
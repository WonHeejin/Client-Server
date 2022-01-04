<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sales</title>
<script src="resources/resource.js"></script>
<link rel="stylesheet" type="text/css" href="resources/common.css"/>
<style>
	body {font-family: 'Gowun Dodum', sans-serif;}
	#info {
	width: 60%;
	height: 25px;
	line-height: 25px;
	background-color: #81BA7B;
	color: #3A3A3A;
	font-size: 10pt;
	text-align: right;
	float: right;
	padding: 0px 4px;
}

.btn {
	width: 70px;
	height: 20px;
	background-color: #EAEAEA;
	font-family: 'Gowun Dodum', sans-serif;
	border: 0px;
	color: #3A3A3A;
	font-size: 12px;
	cursor: pointer;
}

#infoLogo {
	width: 100%;
	height: 25px;
	background: #81BA7B;
}

#logo {
	padding: 0px 4px;
	width: 20%;
	height: 25px;
	line-height: 25px;
	background-color: #81BA7B;
	color: #3A3A3A;
	font-size: 10pt;
	text-align: left;
	font-weight: 800;
	font-size: 15px;
	float: left;
}
	#data {width:100%;height:570px;float:right;background-color: #EAEAEA;border:0px solid #EAEAEA;}
	#footer {position:absolute; top:93%;width: 98.7%; height: 25px; line-height: 30px;
		  background-color: #81BA7B; border:2px solid #81BA7B;
		  color: #3A3A3A; font-size:12pt;
		  text-align:right;}  
</style>
</head>
<body>
	<div id="infoLogo">
		<div id="logo">WEB POS</div>
		<div id="info">
			${accessInfo[0].sename}(${accessInfo[0].secode}) <span>${accessInfo[0].emName}(${accessInfo[0].emcode})</span>
			최근 접속 일자 ${accessInfo[0].date} <span><input type="button"
				class="btn"
				onClick="accessOut('${accessInfo[0].secode}','${accessInfo[0].emcode}')"
				value="로그아웃" /></span>
		</div>
	</div>
	<div id="data">
		
	</div>
	<div id="footer">made by jean</div>
</body>
</html>
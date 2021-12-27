<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main Page :: Dynamic :: AccessInfo</title>
 <script src="resources/resource.js"></script>
<script>
	//get방식은 location.href를 이용하는게 더 간편하다.
 function accessOut(seCode,emCode){
	location.href="AccessOut?seCode="+seCode+"&emCode="+emCode;
} 
 function mouseOver(obj){
/* 	 let styleName = (obj.id == "mgt")? "mgtOver": "salesOver";
		obj.className = styleName; */
		obj.style.color = "#FFFFFF"
		obj.style.backgroundColor=(obj.id == "mgt")? "#7F7CC9": "#EDCE7A"
 }
 function mouseLeave(obj){
	 let fColor = (obj.id == "mgt")? "#6D6AB7": "#E0B94F";
	/*  obj.className="select"; */
	 obj.style.color = fColor;
	 obj.style.backgroundColor="#F6F6F6";
 }
 function moveService(action, seCode, emCode){
	 const form= makeForm("",action,"post");
	 const pSeCode = makeInputElement("hidden","seCode",seCode,"");
	 const pEmCode = makeInputElement("hidden","emCode",emCode,"");
	
 	 form.appendChild(pSeCode);
 	 form.appendChild(pEmCode);
 
 	 document.body.appendChild(form);
 	 form.submit();
 }
</script>
<link rel="stylesheet" type="text/css" href="resources/common.css"/>
<style>
	body {font-family: 'Gowun Dodum', sans-serif;}
	span {margin:5px;}
	#info {width:88%;height:25px; line-height:25px;
		  background-color: #81BA7B; border:2px solid #81BA7B;
		  color: #3A3A3A; font-size:10pt;
		  text-align:right;float:right;}
	#logo {padding:0px 5px;width:10.575%;height:25px; line-height:25px; background-color: #81BA7B;
			border:2px solid #81BA7B;
		  	color: #3A3A3A; font-size:10pt;
			text-align:left;font-weight:800;font-size:15px;float:left;}
	 
	.select,.mgtOver,.salesOver {width:40%;height:200px;text-align:center;
			 					 line-height:200px;font-size:26pt;font-weight:800;
			 					 background-color: #EDCE7A}	 
	
	.btn {width:70px; height:20px; background-color:#EAEAEA;font-family: 'Gowun Dodum', sans-serif;
		  border: 0px; color:#3A3A3A; font-size:12px;cursor:pointer;}	
	#buttonWrap {width:55%;height:200px;
				 position:absolute; top:50%; left:50%; transform: translate(-50%, -50%);}
	#mgt {border:10px solid #7F7CC9;float:left;
		  background-color:#F6F6F6;color: #6D6AB7;cursor:pointer;}
	.mgtOver {background-color: #7F7CC9;}
	#sales {border:10px solid #EDCE7A;float:right;
			background-color:#F6F6F6;color: #E0B94F;cursor:pointer;}
	.salesOver {background-color: #00B700;
						border: 2px solid #00B700;}
	#footer {position:absolute; top:93%;width: 98.7%; height: 25px; line-height: 30px;
		  background-color: #81BA7B; border:2px solid #81BA7B;
		  color: #3A3A3A; font-size:12pt;
		  text-align:right;}  	 
</style>
</head>
<body>
	<div id="info" >
		${accessInfo[0].sename}(${accessInfo[0].secode})
		<span>${accessInfo[0].emName}(${accessInfo[0].emcode})</span>
		최근 접속 일자 ${accessInfo[0].date}
		<span><input type="button" class="btn" onClick="accessOut('${accessInfo[0].secode}','${accessInfo[0].emcode}')" value="로그아웃"/></span>		
	</div>
		<div id="logo" >WEB POS</div>
	<div id="buttonWrap">
		<div id="mgt" class="select" onMouseOver="mouseOver(this)" onMouseOut="mouseLeave(this)" onClick="moveService('Management','${accessInfo[0].secode}','${accessInfo[0].emcode}')">Management</div>
		<div id="sales" class="select" onMouseOver="mouseOver(this)" onMouseOut="mouseLeave(this)" onClick="moveService('Sales','${accessInfo[0].secode}','${accessInfo[0].emcode}')">Sales</div>
	</div>
		<input type="hidden" name="seCode" value="${accessInfo[0].secode}"/>
		<input type="hidden" name="emCode" value="${accessInfo[0].emcode}"/>
	<div id="footer">made by jean</div>	
</body>
</html>
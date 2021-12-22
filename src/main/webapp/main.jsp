<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main Page :: Dynamic :: AccessInfo</title>
 <script src="resources/resource.js"></script>
<script>
	function accessOut(secode, emcode){
			
		const form=makeForm("","AccessOut","get");
		const inputSeCode=makeInputElement("hidden","seCode",secode,"");
		const inputEmCode=makeInputElement("hidden","emCode",emcode,"");
		form.appendChild(inputSeCode);
		form.appendChild(inputEmCode);
		
		document.body.appendChild(form);
		form.submit();
	}
	//get방식은 location.href를 이용하는게 더 간편하다.
 function accessOut2(seCode,emCode){
	location.href="AccessOut?seCode="+seCode+"&emCode="+emCode;
} 
</script>

</head>
<body>
	<h1>${accessInfo[0].sename}(${accessInfo[0].secode})</h1>
	<h1>${accessInfo[0].emName}(${accessInfo[0].emcode})</h1>
	<h1>최근 접속 일자 ${accessInfo[0].date}</h1>

		
		<input type="hidden" name="seCode" value="${accessInfo[0].secode}"/>
		<input type="hidden" name="emCode" value="${accessInfo[0].emcode}"/>
		<input type="button" onClick="accessOut2('${accessInfo[0].secode}','${accessInfo[0].emcode}')" value="로그아웃"/>
</body>
</html>
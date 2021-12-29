<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Management</title>
<script src="resources/resource.js"></script>
<script>
	function getList(action,seCode,emCode){
		const form=makeForm("", action, "post");
		const input1=makeInputElement("hidden", "seCode", seCode, "");
		const input2=makeInputElement("hidden", "emCode", emCode, "");
		form.appendChild(input1);
		form.appendChild(input2);
		document.body.appendChild(form);
		form.submit();
	}
	function modEmp(seCode,emCode){
		alert(seCode+":"+emCode);
	}
	
	function modCu(cuCode){
		alert(cuCode);
	}
	
	function modGo(GoCode){
		alert(GoCode);
	}
	function init(objName){
		if(objName!=""){
			document.getElementById(objName).click();
		}
	}
</script>
<link rel="stylesheet" type="text/css" href="resources/common.css" />
<style>
body {
	font-family: 'Gowun Dodum', sans-serif;
}

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
#infoLogo {width:100%;height: 25px;background:#81BA7B;}
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

#index {
	width: 15%;
	height: 570px;
	clear:both;
	float: left;
	background-color: #81BA7B;
	border: 0px solid #81BA7B;
	text-align: center;
}

#data {
	width: 85%;
	height: 570px;
	float: right;
	background-color: #EAEAEA;
	border: 0px solid #EAEAEA;
	text-align:center;
}
#footer {
	clear: both;
	position: absolute;
	top: 93%;
	width: 98.463%;
	height: 25px;
	line-height: 30px;
	background-color: #81BA7B;
	border: 2px solid #81BA7B;
	color: #3A3A3A;
	font-size: 10pt;
	text-align: right;
}

h2 {
	font-size: 15pt;
	margin: 20px 0 20px 10px;
}

.menuContainer {
	width: 90%;
	margin: 0 auto;
}

.managements {
	margin-bottom: 12pt;
	background: #fff;
}

.menuTitle {
	padding: 5px;
	color: #fff;
	background: #333;
	cursor: pointer;
	text-align: center;
}

.items {
	padding: 0px 20px 3px 10px;
	font-size: 10pt;
	text-align: center;
	background: #fff;cursor:pointer;
}
</style>

</head>
<body onLoad="init('${objName}')">
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
	
	<div id="index">
		<div class="menuContainer">
			<h2>${accessInfo[0].sename}</h2>
			<section class="menuContainer">
				<article class="managements Active">
					<!-- Open&Close, DashBoard -->
					<p class="menuTitle">Daily Report</p>
				</article>
				<article class="managements">
					<p class="menuTitle" id="salesManagements">영업관리</p>
					<div class="items">
						<p>금월매출정보</p>
						<p>상품매출정보</p>
						<p>요일매출정보</p>
						<p>회원매출정보</p>
					</div>
				</article>
				<article class="managements">
					<p class="menuTitle" id="empManagements">직원관리</p>
					<div class="items">
						<p><span onClick="getList('EmpList','${accessInfo[0].secode}','${accessInfo[0].emcode}')">직원리스트</span></p>
						<p>직원정보등록</p>
						<p>직원정보수정</p>
					</div>
				</article>
				<article class="managements">
					<p class="menuTitle" id="cuManagements">회원관리</p>
					<div class="items">
						<p><span onClick="getList('CuList','${accessInfo[0].secode}','${accessInfo[0].emcode}')">회원리스트</span></p>
						<p>회원정보등록</p>
						<p>회원정보수정</p>
					</div>
				</article>
				<article class="managements">
					<p class="menuTitle" id="goManagements">상품관리</p>
					<div class="items">
						<p><span onClick="getList('GoList','${accessInfo[0].secode}','${accessInfo[0].emcode}')">상품리스트</span></p>
						<p>상품정보등록</p>
						<p>상품정보수정</p>
					</div>
				</article>
			</section>
		</div>
	</div>
	<div id="data">${list}</div>
	<div id="footer">made by jean</div>
</body>
<script>
 /* 메누에 관련 항목을 클래스 이름으로 연결 */
  let menuZone = document.getElementsByClassName("managements");
  let menuItems = document.getElementsByClassName("items");
  let menuTitle = document.getElementsByClassName("menuTitle");
  /* menuZone의 Tag는 표식인 <article>로써 기본 이벤트가 없으므로 이벤트를 부여
  :addEventListener('이벤트명',function(e){이벤트 발생 시 실행 구문}) */
  for(let titleIdx=0; titleIdx<menuTitle.length;titleIdx++){
	  menuTitle[titleIdx].addEventListener("click",  function(e){
			 for(let zoneIdx=0;zoneIdx<menuZone.length;zoneIdx++){
					menuZone[zoneIdx].classList.remove("Active");	
			  		}
				 e.target.parentNode.classList.add("Active");
				 activateItems();
			 });	
  }
   /* 각 메뉴의 items가 안 보이도록 display: none
   주메뉴의 items만 보이도록 display: block */
  function activateItems(){
	  for(let itemsIdx=0; itemsIdx<menuItems.length; itemsIdx++){
		  menuItems[itemsIdx].style.display = "none";
	  }
	  const activeItems = document.querySelector(".managements.Active .items");
	  if(activeItems!=null){
		  activeItems.style.display="block";  
	  }
  }
  activateItems();
 </script>
</html>
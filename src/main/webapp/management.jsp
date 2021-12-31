<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Management</title>
<script src="resources/resource.js"></script>
<script>
	function getList(action, seCode, emCode) {
		const form = makeForm("", action, "post");
		const input1 = makeInputElement("hidden", "seCode", seCode, "");
		const input2 = makeInputElement("hidden", "emCode", emCode, "");
		form.appendChild(input1);
		form.appendChild(input2);
		document.body.appendChild(form);
		form.submit();
	}
	function init(objName) {
		if (objName != "") {
			document.getElementById(objName).click();
		}
	}
	/* AJAX : Asynchronous Javascript And XML 
	 1. XMLHttpRequest 객체 생성
	 2. onReadyStateChange 속성 사용 --> 서버와의 통신 내용 설정 ==> function
	    ajax.readyState : 0 - 초기화
										1 - 로딩중
										2 - 로딩완료
										3 - 서버와의 통신중
										4 - 서버로부터 데이터 전송 받음 
	    ajax.status     : 200 - 전송 중 에러 없음
	    									400 - 전송 중 에러 :: 클라이언트로 보낼 페이지가 없음
	    	
	 			5. 서버로부터 데이터를 넘겨 받기 --> responseText
	 3. 생성된 XMLHttpRequest 객체의 Open() 
	 4. Open 된 XMLHttpRequest 객체를 서버로 Send()
	 */
	function getAjaxData(action, data) {
		let ajax = new XMLHttpRequest();
		ajax.onreadystatechange = function() {
			if (ajax.readyState == 4 && ajax.status == 200) {
				let serverData = ajax.responseText;
				if(serverData.substr(0,1)=="<"){
					document.getElementById("ajaxData").innerHTML = serverData;	
				}else{
					document.getElementById(serverData).click();
				}
				
				
			}
		};
		ajax.open("post", action, true);
		ajax.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		
		ajax.send(data);
	}
	function getEmpForm(action,pSeCode){
		
		const data ="seCode="+ encodeURIComponent(pSeCode);
		
		getAjaxData(action,data);
	}
	function RegEmp(seCode, emCode) {
		const emName=document.getElementsByName("emName")[0].value;
		const emPass=document.getElementsByName("emPass")[0].value;
		const data = "seCode="+encodeURIComponent(seCode)
					+ "&emCode="+encodeURIComponent(emCode)
					+ "&emName="+encodeURIComponent(emName)
					+ "&emPass="+encodeURIComponent(emPass);
		getAjaxData("RegEmp",data);
	}
	function getMmbForm(action){
		getAjaxData(action,null);
	}
	function RegMmb(cuCode) {
		const cuName=document.getElementsByName("cuName")[0].value;
		const cuPhone=document.getElementsByName("cuPhone")[0].value;
		const cuClCode=document.getElementsByName("cuClCode")[0].value;
		const data="cuCode="+encodeURIComponent(cuCode)
					+"&cuName="+encodeURIComponent(cuName)
					+"&cuPhone="+encodeURIComponent(cuPhone)
					+"&cuClCode="+encodeURIComponent(cuClCode);
		getAjaxData("RegMmb",data);
		 
	}
	function getGoForm(action){
		getAjaxData(action,null);
	}
	function RegGo() {
		const goCode=document.getElementsByName("goCode")[0].value;
		const goName=document.getElementsByName("goName")[0].value;
		const goCost=document.getElementsByName("goCost")[0].value;
		const goPrice=document.getElementsByName("goPrice")[0].value;
		const goStocks=document.getElementsByName("goStocks")[0].value;
		const goDiscount=document.getElementsByName("goDiscount")[0].value;
		const goCaCode=document.getElementsByName("goCaCode")[0].value;
		const goState=document.getElementsByName("goState")[0].value;
		const data="goCode="+encodeURIComponent(goCode)
					+"&goName="+encodeURIComponent(goName)
					+"&goCost="+encodeURIComponent(goCost)
					+"&goPrice="+encodeURIComponent(goPrice)
					+"&goStocks="+encodeURIComponent(goStocks)
					+"&goDiscount="+encodeURIComponent(goDiscount)
					+"&goCaCode="+encodeURIComponent(goCaCode)
					+"&goState="+encodeURIComponent(goState);
		getAjaxData("RegGo",data);
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

#index {
	width: 15%;
	height: 570px;
	clear: both;
	float: left;
	background-color: #81BA7B;
	border: 0px solid #81BA7B;
	text-align: center;
}

#ajaxData {
	width: 85%;
	height: 570px;
	float: right;
	background-color: #EAEAEA;
	border: 0px solid #EAEAEA;
	text-align: center;
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
	background: #fff;
	cursor: pointer;
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
						<p>
							<span id="EmpList"
								onClick="getList('EmpList','${accessInfo[0].secode}','${accessInfo[0].emcode}')">직원리스트</span>
						</p>
						<p>
							<span onClick="getEmpForm('RegEmpForm','${accessInfo[0].secode}')">직원정보등록</span>
						</p>
						<p>직원정보수정</p>
					</div>
				</article>
				<article class="managements">
					<p class="menuTitle" id="cuManagements">회원관리</p>
					<div class="items">
						<p>
							<span id="MmbList"
								onClick="getList('CuList','${accessInfo[0].secode}','${accessInfo[0].emcode}')">회원리스트</span>
						</p>
						<p>
							<span onClick="getMmbForm('RegMmbForm')">회원정보등록</span>
						</p>
						<p>회원정보수정</p>
					</div>
				</article>
				<article class="managements">
					<p class="menuTitle" id="goManagements">상품관리</p>
					<div class="items">
						<p>
							<span id="GoList"
								onClick="getList('GoList','${accessInfo[0].secode}','${accessInfo[0].emcode}')">상품리스트</span>
						</p>
						<p>
							<span onClick="getGoForm('RegGoForm')">상품정보등록</span>
						</p>
						<p>상품정보수정</p>
					</div>
				</article>
			</section>
		</div>
	</div>
	<div id="ajaxData">${list}</div>
	<div id="footer">made by jean</div>
</body>
<script>
	/* 메누에 관련 항목을 클래스 이름으로 연결 */
	let menuZone = document.getElementsByClassName("managements");
	let menuItems = document.getElementsByClassName("items");
	let menuTitle = document.getElementsByClassName("menuTitle");
	/* menuZone의 Tag는 표식인 <article>로써 기본 이벤트가 없으므로 이벤트를 부여
	:addEventListener('이벤트명',function(e){이벤트 발생 시 실행 구문}) */
	for (let titleIdx = 0; titleIdx < menuTitle.length; titleIdx++) {
		menuTitle[titleIdx].addEventListener("click", function(e) {
			for (let zoneIdx = 0; zoneIdx < menuZone.length; zoneIdx++) {
				menuZone[zoneIdx].classList.remove("Active");
			}
			e.target.parentNode.classList.add("Active");
			activateItems();
		});
	}
	/* 각 메뉴의 items가 안 보이도록 display: none
	주메뉴의 items만 보이도록 display: block */
	function activateItems() {
		for (let itemsIdx = 0; itemsIdx < menuItems.length; itemsIdx++) {
			menuItems[itemsIdx].style.display = "none";
		}
		const activeItems = document
				.querySelector(".managements.Active .items");
		if (activeItems != null) {
			activeItems.style.display = "block";
		}
	}
	activateItems();
</script>
</html>
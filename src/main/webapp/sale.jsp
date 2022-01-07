<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sales</title>
<script src="resources/resource.js"></script>
<script src="resources/sale.js"></script>
<link rel="stylesheet" type="text/css" href="resources/common.css" />
<link rel="stylesheet" type="text/css" href="Sales.css" />
</head>
<body>
	<div id="top">
		${accessInfo[0].sename}(${accessInfo[0].secode}) <span>${accessInfo[0].emName}(${accessInfo[0].emcode})</span>
		최근 접속 일자 ${accessInfo[0].date} <span><input type="button"
			class="btn"
			onClick="accessOut('${accessInfo[0].secode}','${accessInfo[0].emcode}')"
			value="로그아웃" /></span>
	</div>
	<div id="main">
		<div id="red1">
			<div id="green1">
				<div id="puple1">
					<div id="listTop">
						<div class="wtf1">No</div>
						<div class="wtf2">상품명</div>
						<div class="wtf3">수량</div>
						<div class="wtf4">단가</div>
						<div class="wtf5">금액</div>
						<div class="wtf6">할인</div>
					</div>
					<div id="list"></div>
					<div id="amount">합계</div>
					<div id="price" >
						<div style="float: left;width: 48%;height: 9.5%;" name="sum"></div>
						<div style="float: left;width: 30%;height: 9.5%;" name="sum"></div>
						<div style="float: left;width: 22%;height: 9.5%;" name="sum"></div>
					</div>
				</div>
				<div id="puple2">
					<div id="up" onClick="modQty(1,'list')">+</div>
					<div id="down" onClick="modQty(-1,'list')">-</div>
					<div id="delete" onClick="delBotton('list')">삭제</div>
				</div>
			</div>
			<div id="green2">
				<div id=inputGoCode>
					<input type="text" id="goCode" name="prCode" placeholder="상품코드 입력" />
					<input type="button" id="btn"
						onClick="goodsInfoCtl('getGocode','addGoods')"
						value="검색" />
				</div>
			</div>
		</div>
		<div id="red2">
			<div id="green3">
				<div class="inputCuCode">주문금액</div>
				<div class="box"></div>
				<div class="inputCuCode">할인금액</div>
				<div class="box"></div>
				<div class="inputCuCode">받을금액</div>
				<div class="box"></div>
				<div class="inputCuCode">고객코드입력</div>
			</div>
			<div id="green4">
				<div id="pay">결제</div>
				<div id="cancle" onClick="cancle()">취소</div>
			</div>
		</div>
	</div>
	<div id="footer">made by jean</div>
</body>
</html>
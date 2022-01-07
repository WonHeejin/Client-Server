/**
 * client에서 Node를 활용하여 데이터 조정
 */
const itemName=["no","prName","prQuantity","prPrice","prAmount","prDiscount","goCode"];
let no =0;
let currentRecord=null;

function goodsInfoCtl(action,fn){ 
	const prCode= document.getElementsByName("prCode")[0].value;
	if(prCode!=""){
		if(!comparePrCode(prCode,"record")){
		const data="goCode="+encodeURIComponent(prCode);
		getAjaxData(action,data,fn);
		}
	}else{
		alert("상품코드 입력");
	}
	goCode.value="";
	goCode.focus();

}
function getAjaxData(action,data,fn){
	const ajax = new XMLHttpRequest();
		ajax.onreadystatechange = function() {
			if ( ajax.readyState== 4 && ajax.status == 200) {			
				let serverData=ajax.responseText;
				if(serverData.substr(0,1)=="["){
					window[fn](serverData);
				}else{
					alert("실패");
				}
					
			}
		};
		ajax.open("post", action, true);
		ajax.setRequestHeader("Content-type","application/x-www-form-urlencoded");	
		ajax.send(data);
}
function comparePrCode(prCode,list){
	let check = false;
	const record=document.getElementsByName(list);
	for(idx=0;idx<record.length;idx++){
		if(record[idx].childNodes[6].innerText==prCode){
			const qty=record[idx].childNodes[2].innerText;
			record[idx].childNodes[2].innerText=parseInt(qty)+1;
			record[idx].childNodes[4].innerText=parseInt(record[idx].childNodes[2].innerText)*parseInt(record[idx].childNodes[3].innerText);
			record[idx].childNodes[5].innerText=parseInt(record[idx].childNodes[2].innerText)*(parseInt(record[idx].childNodes[5].innerText)/qty);
			orderList();
			orderList2();
			check=true;
		}
	}
	return check;
}
function addGoods(goodsInfo){
	
	const list = document.getElementById("list");
	let jsonData=JSON.parse(goodsInfo); //JSON.stringfy :: 전달된 데이터를 문자열로 변환 ex)prName:'새우깡'->"prName":"새우깡"
	if(jsonData[0].goState==-1){
		document.getElementsByName("prCode")[0].placeholder="판매불가 상품";
	}else{
		no++;
		let record=createDiv("record","record");
		record.setAttribute("onClick","selectBotton(this)");
		for(colIdx=0;colIdx<7;colIdx++){
		let item = createDiv(itemName[colIdx],"goods "+itemName[colIdx]);
		item.innerHTML=(colIdx==0)?no:(colIdx==1)?jsonData[0].goName:(colIdx==2)?jsonData[0].goQuantity:(colIdx==3)?jsonData[0].goPrice:(colIdx==4)?parseInt(jsonData[0].goQuantity)*parseInt(jsonData[0].goPrice):(colIdx==5)?jsonData[0].goDiscount:jsonData[0].goCode;
		record.appendChild(item);
		}
		list.appendChild(record);
		document.getElementsByName("prCode")[0].placeholder="상품코드 입력";
	
		orderList();
		orderList2();				
	}	
		selectBotton(list.lastChild);		
}
function createDiv(name,className){
	const div=document.createElement("div"); //<div></div>
	div.setAttribute("name",name);
	div.setAttribute("class",className);
	
	return div;
}
function orderList(){
	const quantity=document.getElementsByClassName("prQuantity");
	const price=document.getElementsByClassName("prPrice");
	const discount=document.getElementsByClassName("prDiscount");
	let sumObj=document.getElementsByName("sum");
	let sum=[null,null,null];
	for(idx=0;idx<quantity.length;idx++){	//적용하려는 곳에 공백 있으면 innerText 적용이 안됨
		sum[0]+=parseInt(quantity[idx].innerText);
		sum[1]+=parseInt(price[idx].innerText)*parseInt(quantity[idx].innerText);
		sum[2]+=parseInt(discount[idx].innerText);
	}
	for(idx=0;idx<sum.length;idx++){
		sumObj[idx].innerText=sum[idx];
	}
	
}
function orderList2(){
	const quantity=document.getElementsByClassName("prQuantity");
	const price=document.getElementsByClassName("prPrice");
	const discount=document.getElementsByClassName("prDiscount");
	let sumObj=document.getElementsByClassName("box");
	let sum=[null,null,null];
	for(idx=0;idx<quantity.length;idx++){	
		sum[0]+=parseInt(price[idx].innerText)*parseInt(quantity[idx].innerText);
		sum[1]+=parseInt(discount[idx].innerText);
		sum[2]+=(parseInt(price[idx].innerText)*parseInt(quantity[idx].innerText))-(parseInt(discount[idx].innerText));
	}
	for(idx=0;idx<sum.length;idx++){
		sumObj[idx].innerText=sum[idx];
	}
	
}
//hasChildNodes()
function delBotton(objName){
	if(currentRecord!=null){
		currentRecord.remove();
		no--;
		resetNo();
	}else{
		const obj=document.getElementById(objName);
		obj.removeChild(obj.lastChild);
		no--;
	}
	currentRecord=null;
	orderList();
	orderList2();
}
function selectBotton(obj){
	if(currentRecord!=null){
		currentRecord.style.color="black";
		currentRecord=null;
	}
	currentRecord=obj;
	obj.style.color="red";
}
function modQty(qty,objName){
	let childList;
	const obj=document.getElementById(objName);
	if(currentRecord!=null){
		childList=currentRecord.childNodes;
	}else{
		childList=obj.lastChild.childNodes;
	}	
	quantity=parseInt(childList[2].innerText)+qty;
	if(quantity!=0){	
		childList[2].innerText=(quantity).toString();
		childList[4].innerText=(quantity*parseInt(childList[3].innerText)).toString();
		childList[5].innerText=childList[5].innerText/(quantity-qty)*quantity;	
	}else{
		delBotton(objName);
	}
	orderList();
	orderList2();
}
function resetNo(){
	let list=document.getElementById("list").childNodes;
	for(index=0;index<list.length;index++){
		let subList=list[index].childNodes;
		subList[0].innerText=index+1;
	}
}	
function cancle(){
	const list=document.getElementById("list");
	while(list.hasChildNodes()){
		list.removeChild(list.lastChild);
	}
	no=0;
	orderList();
	orderList2();
}
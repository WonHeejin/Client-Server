/**
 * 
 */
const itemName=["no","prName","prQuantity","prPrice","prAmount","prDiscount"];
let no =0;
let currentRecord=null;
function goodsInfoCtl(){
		currentRecord=null;
	const prCode= document.getElementsByName("prCode");
	
	addGoods({prName:'새우깡',prQuantity:'1',prPrice:'2000',prDiscount:'200'});
	orderList();
	orderList2();
}


function addGoods(goodsInfo){
	no++;
	const list = document.getElementById("list");
	let jsonData=JSON.parse(JSON.stringify(goodsInfo)); //JSON.stringfy :: 전달된 데이터를 문자열로 변환 ex)prName:'새우깡'->"prName":"새우깡"
	let record=createDiv("record","record");
	record.setAttribute("onClick","selectBotton(this)");
	for(colIdx=0;colIdx<6;colIdx++){
		
		let item = createDiv(itemName[colIdx],"goods "+itemName[colIdx]);
		item.innerHTML=(colIdx==0)?no:(colIdx==1)?jsonData.prName:(colIdx==2)?jsonData.prQuantity:(colIdx==3)?jsonData.prPrice:(colIdx==4)?parseInt(jsonData.prQuantity)*parseInt(jsonData.prPrice):jsonData.prDiscount;
		record.appendChild(item);
	}
	list.appendChild(record);
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
		sum[1]+=parseInt(discount[idx].innerText)*parseInt(quantity[idx].innerText);
		sum[2]+=(parseInt(price[idx].innerText)*parseInt(quantity[idx].innerText))-(parseInt(discount[idx].innerText)*parseInt(quantity[idx].innerText));
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
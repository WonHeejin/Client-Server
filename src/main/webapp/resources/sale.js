/**
 * 
 */
const itemName=["no","prName","prQuantity","prPrice","prAmount","prDiscount"];
let no =0;
function goodsInfoCtl(){
	const prCode= document.getElementsByName("prCode");
	
	addGoods({prName:'새우깡',prQuantity:'1',prPrice:'2000',prAmount:'2000',prDiscount:'200'});
	orderList();
	orderList2();
}


function addGoods(goodsInfo){
	no++;
	const list = document.getElementById("list");
	let jsonData=JSON.parse(JSON.stringify(goodsInfo));
	let record=createDiv("goods","record");
	for(colIdx=0;colIdx<6;colIdx++){
		let item = createDiv(itemName[colIdx],"goods "+itemName[colIdx]);
		item.innerHTML=(colIdx==0)?no:(colIdx==1)?jsonData.prName:(colIdx==2)?jsonData.prQuantity:(colIdx==3)?jsonData.prPrice:(colIdx==4)?jsonData.prAmount:jsonData.prDiscount;
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
	const amount=document.getElementsByClassName("prAmount");
	const discount=document.getElementsByClassName("prDiscount");
	let sumObj=document.getElementsByName("sum");
	let sum=[0,0,0];
	for(idx=0;idx<quantity.length;idx++){	
		sum[0]+=parseInt(quantity[idx].innerText);
		sum[1]+=parseInt(amount[idx].innerText);
		sum[2]+=parseInt(discount[idx].innerText);
	}
	for(idx=0;idx<sum.length;idx++){
		sumObj[idx].innerText=sum[idx];
	}
	
}
function orderList2(){
	const amount=document.getElementsByClassName("prAmount");
	const discount=document.getElementsByClassName("prDiscount");
	let sumObj=document.getElementsByClassName("box");
	let sum=[0,0,0];
	for(idx=0;idx<amount.length;idx++){	
		sum[0]+=parseInt(amount[idx].innerText);
		sum[1]+=parseInt(discount[idx].innerText);
		sum[2]+=parseInt(amount[idx].innerText)-parseInt(discount[idx].innerText);
	}
	for(idx=0;idx<sum.length;idx++){
		sumObj[idx].innerText=sum[idx];
	}
	
}

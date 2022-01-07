package service.sales;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import beans.Goods;

public class SalesManagement {
	private HttpServletRequest req;
	public SalesManagement(HttpServletRequest req) {
		this.req=req;
	}
		
	public String backController(String jobCode) {		
		String form=null;
		switch (jobCode) {
		case "pos":
			form=this.getPosGoods();
			break;	
		default:
			
		}
		return form;
	}
	
	private String getPosGoods() {
		String jsonData=null;
		Goods go = new Goods();
		ArrayList<Goods> list=new ArrayList<Goods>();
		go.setGoCode(this.req.getParameter("goCode"));
		DataAccessObject dao = new DataAccessObject();
		Connection con=dao.getConnection();
		list=dao.getGoodsInfo(con, go);
		if(list!=null) {
			jsonData=new Gson().toJson(list);
		}else {jsonData="찾을 수 없는 매장코드입니다.";}
		
		dao.closeConnection(con);		
		return jsonData;
	}
}

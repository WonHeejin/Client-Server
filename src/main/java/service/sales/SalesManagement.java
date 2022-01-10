package service.sales;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.Goods;
import beans.Orders;

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
		case "order":
			form=this.setOrdersCtl();
			break;		
		default:
			
		}
		return form;
	}
	
	private String setOrdersCtl() {
		String message="0";
		HttpSession session=this.req.getSession();
		Orders od= new Orders();
		od.setOdPoSrCode(session.getAttribute("seCode").toString());
		od.setOdPoCode("P2");
		od.setOdCuCode(this.req.getParameter("cuCode"));
		od.setGoCode(this.req.getParameterValues("prCode"));
		od.setGoQuantity(this.req.getParameterValues("prQuantity"));
		/*insert Orders --> return:odCode*/
		 
		DataAccessObject dao=new DataAccessObject();
		Connection con=dao.getConnection();
		boolean tran=false;
		boolean check=true;
		dao.modifyTran(con, false);
		dao.insOrders(con,od);
		if(od.getOdCode()!=null) {
			/* loop : insert OrderDetail */
			for(int idx=0;idx<od.getGoCode().length;idx++) {
				if(!dao.insOrderDetail(con, od, idx)) {
					check=false;
					break;
				}
			}
			if(check) {
				if(dao.updOrderState(con, od)) {
					tran=true;message="1";
				}		
			}
		}
		
		dao.setTran(con, tran);
		
		dao.modifyTran(con, true);
		dao.closeConnection(con);
		return new Gson().toJson(message); //표준 규격을 벗어나면 어떤 브라우저에서는 안뜨기 때문에 규격에 맞춰줘야함.
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

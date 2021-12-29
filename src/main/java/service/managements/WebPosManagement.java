package service.managements;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.ActionBeans;
import beans.Customers;
import beans.Employees;
import beans.Goods;

public class WebPosManagement {
	private HttpServletRequest req;
	private ActionBeans action;
	private HttpSession session;
	
	public WebPosManagement(HttpServletRequest req) {
		this.req=req;
		this.action=new ActionBeans();
	}
	
	public ActionBeans backController(int jobCode) {
		switch (jobCode) {
		case 1:
			action=this.mgrMainCtl();
			break;
		case 6:
			action=this.getEmpListCtl();
			break;	
		case 9:
			action=this.getCuList();
			break;	
		case 12:
			action=this.getGoList();
			break;	
		default:
			
		}
		return action;
	}
	private ActionBeans getGoList() {
		ActionBeans action = new ActionBeans();
		Employees emp=new Employees();
		emp.setSecode(req.getParameter("seCode"));
		emp.setEmcode(req.getParameter("emCode"));
		DataAccessObject dao=new DataAccessObject();
		Connection con=dao.getConnection();
		req.setAttribute("accessInfo", dao.getAccessInfo(con, emp));
		req.setAttribute("list", this.toHttpFromArrayGo(dao.getGoList(con)));
		this.req.setAttribute("objName", "goManagements");
		dao.closeConnection(con);
		action.setPage("management.jsp");
		action.setRedirect(false);
		
		dao.getGoList(con);
		return action;
	}
	
	private ActionBeans getCuList() {
		ActionBeans action=new ActionBeans();
		Employees emp=new Employees();
		emp.setSecode(req.getParameter("seCode"));
		emp.setEmcode(req.getParameter("emCode"));
		DataAccessObject dao=new DataAccessObject();
		Connection con=dao.getConnection();
		req.setAttribute("accessInfo", dao.getAccessInfo(con, emp));
		req.setAttribute("list",  this.toHttpFromArrayCu(dao.getCuList(con)));
		this.req.setAttribute("objName", "cuManagements");
		dao.closeConnection(con);
		action.setPage("management.jsp");
		action.setRedirect(false);
		
		return action;
	}
	private ActionBeans getEmpListCtl() {
		ActionBeans action= new ActionBeans();
		Employees emp= null;
		
				emp=new Employees();		
				emp.setSecode(this.req.getParameter("seCode"));
				emp.setEmcode(this.req.getParameter("emCode"));
		DataAccessObject dao= new DataAccessObject();
		Connection con = dao.getConnection();
		req.setAttribute("accessInfo", dao.getAccessInfo(con, emp));
		this.req.setAttribute("list", this.toHttpFromArrayEm(dao.getEmpList(con, emp)));
		this.req.setAttribute("objName", "empManagements");
		dao.closeConnection(con);
		action.setPage("management.jsp");
		action.setRedirect(false);
		return action;
	}
	private String toHttpFromArrayEm(ArrayList<Employees> list) {
		StringBuffer sb= new StringBuffer();
		sb.append("<table>");
		sb.append("<tr>");
		sb.append("<td>직원코드</td>");
		sb.append("<td>직원이름</td>");
		sb.append("<td>근무여부</td>");
		sb.append("<td>근태상황</td>");
		sb.append("</tr>");
		for(Employees emp:list) {
			sb.append("<tr>");
			sb.append("<td>"+emp.getEmcode()+"</td>");
			sb.append("<td>"+emp.getEmName()+"</td>");
			sb.append("<td>"+emp.getEmStateName()+"</td>");
			sb.append("<td>"+emp.getTodayInfo()+"</td>");
			sb.append("</tr>");
		}
		sb.append("<td colspan=\'4\'></td>");
		sb.append("<td><input type=\"button\" value=\"직원수정\" onClick=\"modEmp()\"/></td>");
		sb.append("<td><input type=\"button\" value=\"직원등록\" onClick=\"modEmp()\"/></td>");
		sb.append("</table>");
		
		return sb.toString();
	}
	private String toHttpFromArrayCu(ArrayList<Customers> list) {
		StringBuffer sb= new StringBuffer();
		sb.append("<table>");
		sb.append("<tr>");
		sb.append("<td>회원코드</td>");
		sb.append("<td>회원이름</td>");
		sb.append("<td>전화번호</td>");
		sb.append("<td>회원등급</td>");
		sb.append("</tr>");
		for(Customers cu:list) {
			sb.append("<tr>");
			sb.append("<td>"+cu.getCuCode()+"</td>");
			sb.append("<td>"+cu.getCuName()+"</td>");
			sb.append("<td>"+cu.getCuPhone()+"</td>");
			sb.append("<td>"+cu.getCuClName()+"</td>");
			sb.append("</tr>");
		}
		sb.append("<td colspan=\'4\'></td>");
		sb.append("<td><input type=\"button\" value=\"회원수정\" onClick=\"modEmp()\"/></td>");
		sb.append("<td><input type=\"button\" value=\"회원등록\" onClick=\"modEmp()\"/></td>");
		sb.append("</table>");
		
		return sb.toString();
	}
	private String toHttpFromArrayGo(ArrayList<Goods> list) {
		StringBuffer sb= new StringBuffer();
		sb.append("<table>");
		sb.append("<tr>");
		sb.append("<td>상품코드</td>");
		sb.append("<td>상품이름</td>");
		sb.append("<td>구매가격</td>");
		sb.append("<td>판매가격</td>");
		sb.append("<td>할인율</td>");
		sb.append("<td>재고</td>");
		sb.append("<td>분류</td>");
		sb.append("<td>판매상태</td>");
		sb.append("</tr>");
		for(Goods go:list) {
			sb.append("<tr>");
			sb.append("<td>"+go.getGoCode()+"</td>");
			sb.append("<td>"+go.getGoName()+"</td>");
			sb.append("<td>"+go.getGoCost()+"</td>");
			sb.append("<td>"+go.getGoPrice()+"</td>");
			sb.append("<td>"+go.getGoDiscount()+"</td>");
			sb.append("<td>"+go.getGoStocks()+"</td>");
			sb.append("<td>"+go.getGoCaName()+"</td>");
			sb.append("<td>"+go.getGoState()+"</td>");
			sb.append("</tr>");
		}
		sb.append("<td colspan=\'4\'></td>");
		sb.append("<td><input type=\"button\" value=\"상품수정\" onClick=\"modEmp()\"/></td>");
		sb.append("<td><input type=\"button\" value=\"상품등록\" onClick=\"modEmp()\"/></td>");
		sb.append("</table>");
		
		return sb.toString();
	}
	private ActionBeans mgrMainCtl() {
		ActionBeans action=new ActionBeans();
		String page = "index.html";
		boolean isRedirect = true;
		session=req.getSession();
		Employees emp= null;
		ArrayList<Employees> list=null;
		/* Access-Info */
		if(this.req.getParameter("seCode").equals((String)session.getAttribute("seCode"))) {
			if(this.req.getParameter("emCode").equals((String)session.getAttribute("emCode"))) {
				emp=new Employees();
				
				emp.setSecode(this.req.getParameter("seCode"));
				emp.setEmcode(this.req.getParameter("emCode"));
				page="management.jsp";
				isRedirect=false;
			}
		}
		/* DAO */
		DataAccessObject dao= new DataAccessObject();
		Connection con = dao.getConnection();
		if((list=dao.getAccessInfo(con, emp))!=null) {
			req.setAttribute("accessInfo", list);
		}
		dao.closeConnection(con);
		
		action.setPage(page);
		action.setRedirect(isRedirect);
		return action;
	}
}	

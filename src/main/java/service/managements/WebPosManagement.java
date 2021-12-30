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
	public String backController(String jobCode) {
		String form=null;
		switch (jobCode) {
		case "7":
			form=this.regEmpForm();
			break;	
		case "8":
			form=this.regEmp();
			break;	
		case "10":
			form=this.regMmbForm();
			break;	
		case "11":
			form=this.regMmb();
			break;	
		case "13":
			form=this.regGoForm();
			break;	
		default:
			
		}
		return form;
	}
	private String regMmb() {
		String data=null;
		boolean tran=false;
		Customers cu = new Customers();
		cu.setCuCode(this.req.getParameter("cuCode"));
		cu.setCuName(this.req.getParameter("cuName"));
		cu.setCuPhone(this.req.getParameter("cuPhone"));
		cu.setCuClCode(Integer.parseInt(this.req.getParameter("cuClCode")));
		DataAccessObject dao = new DataAccessObject();
		Connection con=dao.getConnection();
		dao.modifyTran(con, false);
		if(dao.insRegMmb(con, cu)) {
			data="MmbList";
			tran=true;
		}else {
			data=this.regMmbForm();
		}
		dao.setTran(con, tran);
		dao.modifyTran(con, true);
		dao.closeConnection(con);
		return data;
	}
	private String regEmp() {	
		Employees emp= new Employees();
		String data= null;
		boolean tran=false;
		/*회원정보 bean에 담기*/
		emp.setSecode(this.req.getParameter("seCode"));
		emp.setEmcode(this.req.getParameter("emCode"));
		emp.setEmName(this.req.getParameter("emName"));
		emp.setEmpass(this.req.getParameter("emPass"));
		emp.setEmStateCode(3);
		DataAccessObject dao= new DataAccessObject();
		Connection con=dao.getConnection();
		dao.modifyTran(con, false);
		if(dao.insRegEmp(con, emp)) {
			data="EmpList";
			tran=true;
		}else {
			data=this.regEmpForm();
		}
		dao.setTran(con, tran);
		dao.modifyTran(con, true);
		dao.closeConnection(con);
		return data;
	}
	private String regEmpForm() {
		Employees emp = new Employees();
		emp.setSecode(this.req.getParameter("seCode"));
		DataAccessObject dao = new DataAccessObject();
		Connection con=dao.getConnection();
		/* 직원코드 최대값 가져오기 */
		dao.getEmpMax(con, emp);
		dao.closeConnection(con);
		/*직원 등록 양식 작성*/
		return this.makeRegEmpForm(emp);
	}
	private String regMmbForm() {
		Customers cu = new Customers();
		DataAccessObject dao = new DataAccessObject();
		Connection con=dao.getConnection();
		/* 회원코드 최대값 가져오기 */
		dao.getMmbMax(con, cu);
		dao.closeConnection(con);
		/*회원 등록 양식 작성*/
		return this.makeRegMmbForm(cu);
	}
	private String regGoForm() {
		
		/*회원 등록 양식 작성*/
		return this.makeRegGoForm();
		
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
		sb.append("<td><input type=\"button\" value=\"직원등록\" onClick=\"getEmpForm(\'RegEmpForm\',\'"+list.get(0).getSecode()+"\')\"/></td>");
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
		sb.append("<td><input type=\"button\" value=\"회원등록\" onClick=\"getMmbForm(\'RegMmbForm\')\"/></td>");
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
		sb.append("<td><input type=\"button\" value=\"상품등록\" onClick=\"getGoForm(\'RegGoForm\')\"/></td>");
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
	String makeRegEmpForm(Employees emp) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<input type=\"text\" name=\"seCode\" value=\"" + emp.getSecode() + "\" readOnly />");
		sb.append("<input type=\"text\" name=\"emCode\" value=\"" + emp.getEmcode() + "\" readOnly />");
		sb.append("<input type=\"text\" name=\"emName\" placeholder=\"직원이름\" />");
		sb.append("<input type=\"password\" name=\"emPass\" placeholder=\"비밀번호\" />");
		sb.append("<input type=\"button\" value=\"직원등록\" onClick=\"RegEmp('"+emp.getSecode()+"','"+emp.getEmcode()+"')\" />");
				
		return sb.toString();
	}
	String makeRegMmbForm(Customers cu) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<input type=\"text\" name=\"cuCode\" value=\""+cu.getCuCode()+"\" readOnly />");
		sb.append("<input type=\"text\" name=\"cuName\" placeholder=\"회원이름\" />");
		sb.append("<input type=\"text\" name=\"cuPhone\" placeholder=\"전화번호\" />");
		sb.append("<input type=\"text\" name=\"cuClCode\" placeholder=\"등급코드\" />");
		sb.append("<input type=\"button\" value=\"회원등록\" onClick=\"RegMmb('"+cu.getCuCode()+"')\" />");
		return sb.toString();
	}
	String makeRegGoForm() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<input type=\"text\" name=\"goCode\" placeholder =\"상품코드\"/>");
		sb.append("<input type=\"text\" name=\"goName\" placeholder =\"상품이름\"/>");
		sb.append("<input type=\"text\" name=\"goCost\" placeholder =\"구매가격\" />");
		sb.append("<input type=\"text\" name=\"goPrice\" placeholder =\"판매가격\"  />");
		sb.append("<input type=\"text\" name=\"goStocks\" placeholder =\"재고\"  />");
		sb.append("<input type=\"text\" name=\"goDiscount\" placeholder =\"할인율\"  />");
		sb.append("<input type=\"text\" name=\"goCaCode\" placeholder =\"분류\"  />");
		sb.append("<input type=\"text\" name=\"goState\" placeholder =\"판매상태\"  />");
		sb.append("<input type=\"button\" value=\"상품등록\" onClick=\"RegGo()\" />");
		return sb.toString();
	}
}	

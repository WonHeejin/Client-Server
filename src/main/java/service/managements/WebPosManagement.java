package service.managements;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.ActionBeans;
import beans.Employee;

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
		default:
			
		}
		return action;
	}
	private ActionBeans getEmpListCtl() {
		ActionBeans action= new ActionBeans();
		ArrayList<Employee> list=null;
		Employee emp= null;
		
		session=req.getSession();	
				emp=new Employee();		
				emp.setSecode(this.req.getParameter("seCode"));
				emp.setEmcode(this.req.getParameter("emCode"));
		DataAccessObject dao= new DataAccessObject();
		Connection con = dao.getConnection();
		req.setAttribute("accessInfo", dao.getAccessInfo(con, emp));
		this.req.setAttribute("list", this.toHttpFromArray(dao.getEmpList(con, emp)));
		dao.closeConnection(con);
		action.setPage("management.jsp");
		action.setRedirect(false);
		return action;
	}
	private String toHttpFromArray(ArrayList<Employee> list) {
		StringBuffer sb= new StringBuffer();
		sb.append("<table>");
		sb.append("<tr>");
		sb.append("<td>직원코드</td>");
		sb.append("<td>직원이름</td>");
		sb.append("<td>근무여부</td>");
		sb.append("<td>근태상황</td>");
		sb.append("<td>정보수정</td>");
		sb.append("</tr>");
		for(Employee emp:list) {
			sb.append("<tr>");
			sb.append("<td>"+emp.getEmcode()+"</td>");
			sb.append("<td>"+emp.getEmName()+"</td>");
			sb.append("<td>"+emp.getEmStateName()+"</td>");
			sb.append("<td>"+emp.getTodayInfo()+"</td>");
			sb.append("<td><input type=\"button\" value=\"수정\" onClick=\"modEmp(\'"+emp.getSecode()+",\'"+emp.getEmcode()+"\')\"/></td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		
		return sb.toString();
	}
	
	private ActionBeans mgrMainCtl() {
		ActionBeans action=new ActionBeans();
		String page = "index.html";
		boolean isRedirect = true;
		session=req.getSession();
		Employee emp= null;
		ArrayList<Employee> list=null;
		/* Access-Info */
		if(this.req.getParameter("seCode").equals((String)session.getAttribute("seCode"))) {
			if(this.req.getParameter("emCode").equals((String)session.getAttribute("emCode"))) {
				emp=new Employee();
				
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

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
		default:
			
		}
		return action;
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
			}else {System.out.println(this.req.getParameter("emCode")+"+"+(String)session.getAttribute("emCode"));}
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

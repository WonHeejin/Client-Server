package service.managements;

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
		action.setPage("management.jsp");
		action.setRedirect(false);
		return action;
	}
}	

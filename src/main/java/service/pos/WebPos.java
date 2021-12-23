package service.pos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.ActionBeans;
import beans.Employee;

public class WebPos {
	private HttpServletRequest req;
	private ActionBeans action;
	private HttpSession session;
	
	public WebPos(HttpServletRequest req) {
		this.req=req;
		this.action=new ActionBeans();
	}
	
	public ActionBeans backController(int jobCode) {
		switch (jobCode) {
		case 1:
			break;
		default:
			
		}
		return this.action;
	}
}

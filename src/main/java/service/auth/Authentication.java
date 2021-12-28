package service.auth;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.ActionBeans;
import beans.Employee;

public class Authentication {
	private HttpServletRequest req;
	private Employee emp;
	private ActionBeans action;
	private HttpSession session;
	
	public Authentication(HttpServletRequest req) {
		this.req=req;
		this.action=new ActionBeans();
	}
	
	public ActionBeans backController(int jobCode) {
		switch (jobCode) {
		case 1:
			this.accessCtl();
			break;
		case -1:
			this.accessOutCtl();
			break;
		case 0:
			this.accessAfter();
			break;
		default:
			
		}
		return this.action;
	}
	
	private void accessCtl() {
		//1. 클라이언트 데이터 빈에 담기 >> Employee:: secode, emcode, empass
		ArrayList<Employee> list= null;
		DataAccessObject dao=new DataAccessObject();
		this.emp=new Employee();
		boolean tran=false;
		emp.setSecode(this.req.getParameter("seCode"));
		emp.setEmcode(this.req.getParameter("emCode"));
		emp.setEmpass(this.req.getParameter("emPassword"));
		emp.setStates(9);
		//System.out.println(this.req.getParameter("seCode"));
		/* 2. DAO 연동 
		 *   2-1. EMPLOYEE :: SECODE 존재 여부
		 *   2-2. EMPLOYEE :: EMCODE 존재 여부
		 *   2-3. EMPLOYEE :: PASSWORD일치 여부 :: RETURN : 1  >> P2-4 
		 *   2-4. ACCESSHISTORY : INSERT :: RETURN : 1
		 *   2-5. 정보 취합  --> ARRAYLIST<EMPLOYEE>
		 *   
		 *   *** 로그인 성공 :: main.jsp
		 *       로그인 실패 :: index.html
		 * */
		Connection con=dao.getConnection();
		dao.modifyTran(con, false);
		if(dao.isStore(con, emp)) {
			if(dao.isPassword(con, emp)) {
				if(dao.insAccessHistory(con, emp)) {
					tran=true;
					session=this.req.getSession();
					session.setAttribute("seCode", emp.getSecode());
					session.setAttribute("emCode", emp.getEmcode());
					if((list=dao.getAccessInfo(con, emp))!=null) {
						req.setAttribute("accessInfo", list);
						session.setAttribute("seName", list.get(0).getSename());
						session.setAttribute("emName", list.get(0).getEmName());
						session.setAttribute("date", list.get(0).getDate());
				}
			}
		}
		action.setPage(tran?"main.jsp":"index.html");
		action.setRedirect(tran?false:true);
		dao.setTran(con, tran);
		dao.modifyTran(con, true);
		dao.closeConnection(con);
		}
	}
	private void accessOutCtl() {
		//1. 클라이언트 데이터 빈에 담기 >> Employee:: secode, emcode
		DataAccessObject dao=new DataAccessObject();
		this.emp=new Employee();
		boolean tran=false;
		session = this.req.getSession();
		emp.setSecode(this.req.getParameter("seCode"));
		emp.setEmcode(this.req.getParameter("emCode"));
		emp.setStates(-9);
		Connection con=dao.getConnection();
		dao.modifyTran(con, false);
		if(dao.insAccessHistory(con, emp)) {
				tran=true;	
				session.invalidate();
				}
		action.setPage("index.html");
		action.setRedirect(true);
		dao.setTran(con, tran);
		dao.modifyTran(con, true);
		dao.closeConnection(con);	
	}
	
	private void accessAfter() {
		ArrayList<Employee> list =new ArrayList<Employee>();
		Employee emp=new Employee();
		DataAccessObject dao= new DataAccessObject();
		Connection con = dao.getConnection();
		session=this.req.getSession();
		emp.setSecode((String)session.getAttribute("seCode"));
		emp.setEmcode((String)session.getAttribute("emCode"));
		if((list=dao.getAccessInfo(con, emp))!=null) {
			req.setAttribute("accessInfo", list);
			action.setRedirect(false);
			action.setPage("main.jsp");
		}
	
	}
	
}

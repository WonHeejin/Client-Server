package service.auth;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import beans.ActionBeans;
import beans.Employee;

public class Authentication {
	private HttpServletRequest req;
	private Employee emp;
	private ActionBeans action;
	
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
		default:
			
		}
		return this.action;
	}
	
	private void accessCtl() {
		//1. 클라이언트 데이터 빈에 담기 >> Employee:: secode, emcode, empass
		ArrayList<Employee> list= null;
		this.emp=new Employee();
		emp.setSecode(this.req.getParameter("seCode"));
		emp.setEmcode(this.req.getParameter("emCode"));
		emp.setEmpass(this.req.getParameter("emPassword"));
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
		/*test*/
		emp.setEmName("테스트 직원");
		emp.setDate("2021/12/16/14:60");
		list= new ArrayList<Employee>();
		list.add(emp);
		
		req.setAttribute("accessInfo", list.get(0).getEmName()+"님은"+list.get(0).getDate()+"에 로그인 하였습니다.");
		req.setAttribute("seCode", list.get(0).getSecode());
		req.setAttribute("emCode", list.get(0).getEmcode());
		if(list!=null) {
			this.action.setRedirect(false);
			this.action.setPage("main.jsp");
		}else {
			this.action.setRedirect(true);
			this.action.setPage("index.html");
		}
	}
	
	private void accessOutCtl() {
		//1. 클라이언트 데이터 빈에 담기 >> Employee:: secode, emcode
		ArrayList<Employee> list= null;
		this.emp=new Employee();
		emp.setSecode(this.req.getParameter("seCode"));
		emp.setEmcode(this.req.getParameter("emCode"));
		/* 2. DAO 연동 
		 *   2-1. EMPLOYEE :: SECODE 존재 여부
		 *   2-2. EMPLOYEE :: EMCODE 존재 여부 :: RETURN : 1  >> P2-3 
		 *   2-3. ACCESSHISTORY : INSERT :: RETURN : 1
		 *   2-4. 정보 취합  --> ARRAYLIST<EMPLOYEE>
		 *   
		 *   *** 로그아웃 성공 :: index.html
		 *       로그아웃 실패 :: main.jsp
		 * */
		/*test*/
		list= new ArrayList<Employee>();
		list.add(emp);
		
			this.action.setRedirect(true);
			this.action.setPage("index.html");
		
	}
}

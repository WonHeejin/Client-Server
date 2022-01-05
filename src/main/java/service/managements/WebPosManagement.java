package service.managements;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.ActionBeans;
import beans.Customers;
import beans.Employees;
import beans.Goods;
import beans.Sales;

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
		case "2":
			form=this.monthlySalesInfo();
			break;
		case "3":
			form=this.goodsSalesInfo();
			break;	
		case "7":
			form=this.regEmpForm();
			break;	
		case "8":
			form=this.regEmp();
			break;	
		case "9":
			form=this.modEmpForm();
			break;	
		case "modEmp":
			form=this.modEmp();
			break;			
		case "10":
			form=this.regMmbForm();
			break;	
		case "11":
			form=this.regMmb();
			break;	
		case "12":
			form=this.modMmbFom();
			break;		
		case "modMmb":
			form=this.modMmb();
			break;		
		case "13":
			form=this.regGoForm();
			break;
		case "14":
			form=this.regGo();
			break;	
		case "15":
			form=this.modGoForm();
			break;	
		case "modGo":
			form=this.modGo();
			break;	
		default:
			
		}
		return form;
	}
	
	private String goodsSalesInfo() {
		String jsonData = null;
		Sales sales = new Sales();
		ArrayList<Sales> list=new ArrayList<Sales>();
		
		sales.setSeCode(this.req.getParameter("seCode"));
		DataAccessObject dao = new DataAccessObject();
		Connection con=dao.getConnection();
		list=dao.getGoodsInfo(con, sales);
		jsonData=new Gson().toJson(list);
		dao.closeConnection(con);
		return jsonData;
	}
	private String monthlySalesInfo() {
		String jsonData = null;
		Sales sales = new Sales();
		ArrayList<Sales> list=new ArrayList<Sales>();
		
		sales.setSeCode(this.req.getParameter("seCode"));
		DataAccessObject dao = new DataAccessObject();
		Connection con=dao.getConnection();
		list=dao.getSalesInfo(con, sales);
		jsonData=new Gson().toJson(list);
		dao.closeConnection(con);
		return jsonData;
	}
	private String modGo() {
		String data=null;
		boolean tran=false;
		Goods go = new Goods();
		go.setGoCode(this.req.getParameter("goCode"));
		go.setGoCost(Integer.parseInt(this.req.getParameter("goCost")));
		go.setGoPrice(Integer.parseInt(this.req.getParameter("goPrice")));
		go.setGoDiscount(Integer.parseInt(this.req.getParameter("goDiscount")));
		go.setGoStocks(Integer.parseInt(this.req.getParameter("goStocks")));
		go.setGoCaCode(this.req.getParameter("goCaCode"));
		go.setGoState(Integer.parseInt(this.req.getParameter("goState")));
		
		DataAccessObject dao = new DataAccessObject();
		Connection con=dao.getConnection();
		dao.modifyTran(con, false);
		if(dao.modGo(con, go)) {
			data="GoList";
			tran=true;
		}else {
			data=this.modGoForm();
		}
		
		dao.setTran(con, tran);
		dao.modifyTran(con, true);
		dao.closeConnection(con);
		return data;
	}
	private String modGoForm() {
		String data=null;
		DataAccessObject dao=new DataAccessObject();
		Connection con=dao.getConnection();
		data=this.makeModGoForm(dao.getGoList(con));	
		dao.closeConnection(con);
		return data;
	}
	private String makeModGoForm(ArrayList<Goods> list) {
		StringBuffer sb=new StringBuffer();
		int idx=-1;
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
		sb.append("<td>정보갱신</td>");
		sb.append("</tr>");
		for(Goods go:list) {
			idx++;
			sb.append("<tr>");
			sb.append("<td>"+go.getGoCode()+"</td>");
			sb.append("<td>"+go.getGoName()+"</td>");
			sb.append("<td><input type='text' name='goCost' value='"+go.getGoCost()+"' readOnly /></td>");
			sb.append("<td><input type='text' name='goPrice' value='"+go.getGoPrice()+"' readOnly /></td>");
			sb.append("<td><input type='text' name='goDiscount' value='"+go.getGoDiscount()+"' readOnly /></td>");
			sb.append("<td><input type='text' name='goStocks' value='"+go.getGoStocks()+"' readOnly /></td>");
			sb.append("<td>");
			sb.append("<select name='ca' disabled >");
			sb.append("<option value='A1'"+(go.getGoCaCode().equals("A1")?"selected":"")+">주류</option>");
			sb.append("<option value='A2'"+(go.getGoCaCode().equals("A2")?"selected":"")+">담배류</option>");
			sb.append("<option value='A3'"+(go.getGoCaCode().equals("A3")?"selected":"")+">과자류</option>");
			sb.append("<option value='A4'"+(go.getGoCaCode().equals("A4")?"selected":"")+">음료류</option>");
			sb.append("<option value='A5'"+(go.getGoCaCode().equals("A5")?"selected":"")+">잡화</option>");
			sb.append("<option value='A6'"+(go.getGoCaCode().equals("A6")?"selected":"")+">완구류</option>");
			sb.append("<option value='A7'"+(go.getGoCaCode().equals("A7")?"selected":"")+">빵류</option>");
			sb.append("</select>");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("<select name='st' disabled >");
			sb.append("<option value='1'"+(go.getGoState()==1?"selected":"")+">판매가능</option>");
			sb.append("<option value='-1'"+(go.getGoState()==-1?"selected":"")+">판매불가</option>");
			sb.append("</select>");
			sb.append("</td>");
			sb.append("<td><input type = 'button' value= '수정' onClick='activateGo(this,"+idx+")'/><input type = 'button' name='goBtn' value= '등록' onClick='updGo(\""+go.getGoCode()+"\","+idx+")' disabled /></td>");
			sb.append("</tr>");
		}
		
		sb.append("</table>");
		
		return sb.toString();
	}
	private String modMmb() {
		String data=null;
		boolean tran=false;
		Customers cu = new Customers();
		cu.setCuCode(this.req.getParameter("cuCode"));
		cu.setCuClCode(Integer.parseInt(this.req.getParameter("cuClCode")));
		DataAccessObject dao = new DataAccessObject();
		Connection con=dao.getConnection();
		dao.modifyTran(con, false);
		if(dao.updMmb(con, cu)) {
			System.out.print("here");
			data="MmbList";
			tran=true;
		}else {
			data=this.modMmbFom();
		}
		dao.setTran(con, tran);
		dao.modifyTran(con, true);
		dao.closeConnection(con);
		return data;
	}
	
	private String modMmbFom() {
		DataAccessObject dao=new DataAccessObject();
		Connection con=dao.getConnection();
		String data=this.makeModMmbForm(dao.getMmbCodeList(con));	
		dao.closeConnection(con);
		return data;
	}
	private String makeModMmbForm(ArrayList<Customers> list) {
		StringBuffer sb= new StringBuffer();
		int idx=-1;
		sb.append("<table>");
		sb.append("<tr>");
		sb.append("<td>회원코드</td>");
		sb.append("<td>회원이름</td>");
		sb.append("<td>전화번호</td>");
		sb.append("<td>회원등급</td>");
		sb.append("<td>정보갱신</td>");
		sb.append("</tr>");
		for(Customers cu:list) {
			idx++;
			sb.append("<tr>");
			sb.append("<td>"+cu.getCuCode()+"</td>");
			sb.append("<td>"+cu.getCuName()+"</td>");
			sb.append("<td>"+cu.getCuPhone()+"</td>");
			sb.append("<td>");
			sb.append("<select name='work' disabled >");
			sb.append("<option value='1'"+(cu.getCuClCode()==1?"selected":"")+">VVIP</option>");
			sb.append("<option value='2'"+(cu.getCuClCode()==2?"selected":"")+">VIP</option>");
			sb.append("<option value='3'"+(cu.getCuClCode()==3?"selected":"")+">GOLD</option>");
			sb.append("<option value='4'"+(cu.getCuClCode()==4?"selected":"")+">SILVER</option>");
			sb.append("<option value='5'"+(cu.getCuClCode()==5?"selected":"")+">BRONZE</option>");
			sb.append("<option value='6'"+(cu.getCuClCode()==6?"selected":"")+">X</option>");
			sb.append("</select>");
			sb.append("</td>");
			sb.append("<td><input type = 'button' value= '수정' onClick='activateMmb(this,"+idx+")'/><input type = 'button' name='mmbBtn' value= '등록' onClick='updMmb(\""+cu.getCuCode()+"\","+idx+")' disabled /></td>");
			sb.append("</tr>");
		}	
		sb.append("</table>");
		return sb.toString();
	}
	private String modEmp() {
		Employees emp= new Employees();
		String data= null;
		boolean tran=false;
		/*회원정보 bean에 담기*/
		emp.setSecode(this.req.getParameter("seCode"));
		emp.setEmcode(this.req.getParameter("emCode"));
		emp.setEmpass(this.req.getParameter("emPassword"));
		emp.setEmStateCode(Integer.parseInt(this.req.getParameter("emState")));
		DataAccessObject dao= new DataAccessObject();
		Connection con=dao.getConnection();
		dao.modifyTran(con, false);
		if(dao.updEmp(con, emp)) {
			data="EmpList";
			tran=true;
		}else {
			data=this.modEmpForm();
		}
		dao.setTran(con, tran);
		dao.modifyTran(con, true);
		dao.closeConnection(con);
		return data;
	}
	private String modEmpForm() {
		String data=null;
		Employees emp= new Employees();
		emp.setSecode(this.req.getParameter("seCode"));
		DataAccessObject dao = new DataAccessObject();
		Connection con=dao.getConnection();
		
		data=this.makeModEmpForm(dao.getEmpCodeList(con, emp));
		
		dao.closeConnection(con);
		
		return data;
		
	}

	/*
	 * <input type="password" name="work" value='1234' />
	 *  <select name="work" >
	 * <option value=-1>근무상황</option>
	 *  <option value="3" selected>근무중</option>
	 * <option value="-3">퇴사</option>
	 *  </select> <input type="button" value="전송" onClick="updEmp()" />
	 */
	private String makeModEmpForm(ArrayList<Employees> list) {
		StringBuffer sb= new StringBuffer();
		int idx=-1;
		sb.append("<table>");
		sb.append("<tr>");
		sb.append("<td>매장코드</td>");
		sb.append("<td>직원코드</td>");
		sb.append("<td>직원이름</td>");
		sb.append("<td>비밀번호</td>");
		sb.append("<td>근무여부</td>");
		sb.append("<td>정보갱신</td>");
		sb.append("</tr>");
		for(Employees emp:list) {
			idx++;
			sb.append("<tr>");
			sb.append("<td>"+emp.getSecode()+"</td>");
			sb.append("<td>"+emp.getEmcode()+"</td>");
			sb.append("<td>"+emp.getEmName()+"</td>");
			sb.append("<td>");
			sb.append("<input type='password' name ='emPassword' value='4321' readOnly />");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("<select name = 'work' disabled>");
			sb.append("<option value='3'"+(emp.getStCode()==3?"selected":"")+">근무중</option>");
			sb.append("<option value='-3'"+(emp.getStCode()==-3?"selected":"")+">퇴사</option>");
			sb.append("</select>");
			sb.append("</td>");
			sb.append("<td><input type = 'button' value= '수정' onClick='activateEmp(this,"+idx+")'/><input type = 'button' name='emBtn' value= '등록' onClick='updEmp(\""+emp.getSecode()+"\",\""+emp.getEmcode()+"\","+idx+")' disabled /></td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}
	private String regGo() {
		String data=null;
		boolean tran=false;
		Goods go = new Goods();
		go.setGoCode(this.req.getParameter("goCode"));
		go.setGoName(this.req.getParameter("goName"));
		go.setGoCost(Integer.parseInt(this.req.getParameter("goCost")));
		go.setGoPrice(Integer.parseInt(this.req.getParameter("goPrice")));
		go.setGoDiscount(Integer.parseInt(this.req.getParameter("goDiscount")));
		go.setGoStocks(Integer.parseInt(this.req.getParameter("goStocks")));
		go.setGoCaCode(this.req.getParameter("goCaCode"));
		go.setGoState(Integer.parseInt(this.req.getParameter("goState")));
		
		DataAccessObject dao = new DataAccessObject();
		Connection con=dao.getConnection();
		dao.modifyTran(con, false);
		if(dao.insRegGo(con, go)) {
			data="GoList";
			tran=true;
		}else {
			data=this.regGoForm();
		}
		
		dao.setTran(con, tran);
		dao.modifyTran(con, true);
		dao.closeConnection(con);
		return data;
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
		sb.append("<td><input type=\"button\" value=\"직원수정\" onClick=\"getModEmpForm('ModEmpForm',"+list.get(0).getSecode()+"')\"/></td>");
		sb.append("<td><input type=\"button\" value=\"직원등록\" onClick=\"getEmpForm('RegEmpForm','"+list.get(0).getSecode()+"')\"/></td>");
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
			sb.append("<td>"+go.getGoStName()+"</td>");
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

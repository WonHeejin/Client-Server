package webpos;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.managements.WebPosManagement;

/**
 * Servlet implementation class AjaxController
 */
@WebServlet({"/RegEmpForm","/RegMmbForm","/RegGoForm","/RegEmp","/RegMmb"})
public class AjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public AjaxController() {
        super();
  
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doAjax(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		this.doAjax(request, response);
	}
	
	private void doAjax(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		HttpSession session = req.getSession();
		String jobCode=req.getRequestURI().substring(req.getContextPath().length()+1);
		WebPosManagement wpm=null;
		String ajaxData=null;
		if(session.getAttribute("seCode")!=null) {
			if(jobCode.equals("RegEmpForm")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("7");
			}else if(jobCode.equals("RegEmp")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("8");
			}else if(jobCode.equals("RegMmbForm")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("10");
			}else if(jobCode.equals("RegMmb")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("11");
			}else if(jobCode.equals("RegGoForm")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("13");
			}else {}
		}else {}
		res.setContentType("text/html;charset=utf-8");
		PrintWriter p= res.getWriter();
		p.write(ajaxData);
	}
}
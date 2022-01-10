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
@WebServlet({"/RegEmpForm","/RegMmbForm","/RegGoForm","/RegEmp","/RegMmb","/RegGo",
			 "/ModEmpForm","/ModEmp","/ModMmbForm","/ModMmb","/ModGoForm","/ModGo",
			 "/MSI","/GSI"})
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
				
			}else if(jobCode.equals("ModEmpForm")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("9");
				
			}else if(jobCode.equals("ModEmp")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("modEmp");
				
			}else if(jobCode.equals("RegMmbForm")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("10");
				
			}else if(jobCode.equals("RegMmb")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("11");
				
			}else if(jobCode.equals("ModMmbForm")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("12");
				
			}else if(jobCode.equals("ModMmb")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("modMmb");
				
			}else if(jobCode.equals("RegGoForm")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("13");
				
			}else if(jobCode.equals("RegGo")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("14");
				
			}else if(jobCode.equals("ModGoForm")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("15");
				
			}else if(jobCode.equals("ModGo")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("modGo");
				
			}else if(jobCode.equals("MSI")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("2");
				
			}else if(jobCode.equals("GSI")) {
				wpm=new WebPosManagement(req);
				ajaxData=wpm.backController("3");
				
			}else {}
		}else {}
		res.setContentType("text/html;charset=utf-8");
		PrintWriter p= res.getWriter();
		p.write(ajaxData);
	}
}

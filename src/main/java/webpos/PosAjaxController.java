package webpos;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.sales.SalesManagement;


/**
 * Servlet implementation class PosAjaxController
 */
@WebServlet({"/getGocode","/Orders"})
public class PosAjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public PosAjaxController() {
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
		String ajaxData=null;
		
		if(session.getAttribute("seCode")!=null) {
			if(jobCode.equals("getGocode")) {  
				 SalesManagement smg= new SalesManagement(req);
				 ajaxData=smg.backController("pos");
			}else if(jobCode.equals("Orders")) {  
				 SalesManagement smg= new SalesManagement(req);
				 ajaxData=smg.backController("order");
			}
		}
		res.setContentType("text/html;charset=utf-8");
		PrintWriter p= res.getWriter();
		p.write(ajaxData);
	}

}

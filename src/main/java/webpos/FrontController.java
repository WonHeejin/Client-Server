package webpos;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ActionBeans;
import service.auth.Authentication;


@WebServlet({"/Access","/AccessOut"})
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public FrontController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			this.doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		this.doProcess(request, response);
	}
	private void doProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ActionBeans action = new ActionBeans();
		String jobCode=req.getRequestURI().substring(req.getContextPath().length()+1);
		Authentication auth;
		
		if(jobCode.equals("Access")) {
			//서비스 호출
			auth= new Authentication(req);
			action=auth.backController(1);
//			action.setRedirect(false);
//			action.setPage("main.jsp");
//			
		}else if(jobCode.equals("AccessOut")) {
			//서비스 호출
			auth= new Authentication(req);
			action=auth.backController(-1);
//			action.setRedirect(true);
//			action.setPage("index.html");
		}else {}
		
		if(action.isRedirect()) {
			res.sendRedirect(action.getPage());
		}else {
			RequestDispatcher dp= req.getRequestDispatcher(action.getPage());
			dp.forward(req, res);
		}
	}

}

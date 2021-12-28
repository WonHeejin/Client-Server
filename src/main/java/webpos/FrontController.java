package webpos;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.ActionBeans;
import service.auth.Authentication;
import service.managements.WebPosManagement;
import service.pos.WebPos;


@WebServlet({"/Access","/AccessOut","/S","/Management","/Sales"})
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public FrontController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jobCode=request.getRequestURI().substring(request.getContextPath().length()+1);
		this.doProcess(request, response);
//		if(jobCode.equals("AccessOut")||jobCode.equals("S")) {
//			this.doProcess(request, response);
//		}else {
//			response.sendRedirect("S");
//		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		this.doProcess(request, response);
	}
	private void doProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ActionBeans action = new ActionBeans();
		String jobCode=req.getRequestURI().substring(req.getContextPath().length()+1);
		Authentication auth=null;
		WebPosManagement wpm=null;
		WebPos wp=null;
		HttpSession session=req.getSession();
		if(session.getAttribute("seCode")!=null) {
			if(jobCode.equals("AccessOut")) {
				//서비스 호출		
				auth= new Authentication(req);
				action=auth.backController(-1);
				//				action.setRedirect(true);
				//				action.setPage("index.html");
			}else if(jobCode.equals("Management")){

				wpm= new WebPosManagement(req);
				action=wpm.backController(1);
			}else if(jobCode.equals("Sales")){

				wp= new WebPos(req);
				action=wp.backController(1);

			}else {

				auth= new Authentication(req);
				action=auth.backController(0);

			}
		}else {
			if(jobCode.equals("Access")) {
				//서비스 호출
				auth= new Authentication(req);
				action=auth.backController(1);
				//				action.setRedirect(false);
				//				action.setPage("main.jsp");		
			}else {
				action= new ActionBeans();
				action.setRedirect(true);
				action.setPage("index.html");
			}

		}	
		if(action.isRedirect()) {
			res.sendRedirect(action.getPage());
		}else {
			RequestDispatcher dp= req.getRequestDispatcher(action.getPage());
			dp.forward(req, res);
		}
	}

}

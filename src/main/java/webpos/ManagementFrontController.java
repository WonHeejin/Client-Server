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
import service.managements.WebPosManagement;

/**
 * Servlet implementation class ManagementFrontController
 */
@WebServlet({"/EmpList","/CuList","/GoList"})
public class ManagementFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManagementFrontController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		this.doProcess(request, response);
	}
	private void doProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ActionBeans action = new ActionBeans();
		String jobCode=req.getRequestURI().substring(req.getContextPath().length()+1);
		WebPosManagement wpm=null;
		HttpSession session=req.getSession();
		if(session.getAttribute("seCode")!=null) {
			if(jobCode.equals("EmpList")){

				wpm= new WebPosManagement(req);
				action=wpm.backController(6);

			}else if(jobCode.equals("CuList")){
				wpm= new WebPosManagement(req);
				action=wpm.backController(9);

			}else if(jobCode.equals("GoList")){
				wpm= new WebPosManagement(req);
				action=wpm.backController(12);

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

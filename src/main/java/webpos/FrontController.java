package webpos;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Access")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public FrontController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//id,psw==whj,1234--->main.jsp
		//id,psw!=whj,1234--->index.html
		if(request.getParameter("mID").equals("whj")&&request.getParameter("mPassword").equals("1234")) {
			response.sendRedirect("main.jsp");
		}else {
			response.sendRedirect("index.html");
		}
	}

}

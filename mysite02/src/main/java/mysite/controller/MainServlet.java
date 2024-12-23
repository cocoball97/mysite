package mysite.controller;

import jakarta.servlet.ServletException;
import mysite.controller.action.main.MainAction;

// @WebServlet({"/main", ""})
public class MainServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		String config = getServletConfig().getInitParameter("config");
		System.out.println("MainController.init() called: " + config);
		super.init();
	}
	
	@Override
	protected Action getAction(String actionName) {
		return new MainAction();
	}
	
	// Main action으로 이동
	/*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/main/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	*/


}
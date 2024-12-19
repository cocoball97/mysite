package mysite.controller;

import java.util.Map;

import jakarta.servlet.annotation.WebServlet;
import mysite.controller.action.guestbook.AddAction;
import mysite.controller.action.guestbook.DeleteAction;
import mysite.controller.action.guestbook.DeleteFormAction;
import mysite.controller.action.guestbook.ListAction;

@WebServlet("/guestbook")
public class GuestbookServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Action> mapAction = Map.of(
			"add", new AddAction(), 
			"deleteform", new DeleteFormAction(),
			"delete", new DeleteAction()
	);
	
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new ListAction());
	}

	/*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String action = request.getParameter("a");
		if("add".equals(action)) {
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String contents = request.getParameter("contents");
			
			GuestbookVo vo = new GuestbookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setContents(contents);
			
			new GuestbookDao().insert(vo);

			response.sendRedirect(request.getContextPath() +"/guestbook");
		} else if("deleteform".equals(action)){
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/deleteform.jsp");
			rd.forward(request, response);
		} else if("delete".equals(action)){
			Long id = Long.parseLong(request.getParameter("id"));
			String password = request.getParameter("password");
			new GuestbookDao().deleteByIdAndPassword(id, password);
			response.sendRedirect(request.getContextPath() +"/guestbook");
		} else {
			List<GuestbookVo> list = new GuestbookDao().findAll();
			request.setAttribute("list", list);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/list.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	*/
}
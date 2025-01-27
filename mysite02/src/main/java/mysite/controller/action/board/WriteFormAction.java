package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class WriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		
		
		if(id != null) {
			BoardVo vo = new BoardDao().findById(Long.parseLong(id));
			request.setAttribute("vo", vo);
		}
		
				
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/write.jsp");
		rd.forward(request, response);
	}

}


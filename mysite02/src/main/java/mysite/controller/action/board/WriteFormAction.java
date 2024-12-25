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
		
//		String id = request.getParameter("id");
//		String o_no = request.getParameter("o_no");
//		String dept = request.getParameter("dept");
		
		
//		Long maxGNo = new BoardDao().findMaxGNo();
		// 처리하고 find하자
		
		
//		BoardVo vo = new BoardDao().findById(Long.parseLong(id));

//		vo.setO_no(Long.parseLong(o_no));
//		vo.setDept(Long.parseLong(dept));
		
//		request.setAttribute("vo", vo);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/write.jsp");
		rd.forward(request, response);
	}

}


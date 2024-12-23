package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		
		
		// 세션이 없는 경우
		if (session == null) {
		    response.sendRedirect(request.getContextPath() + "/board");
		    return;
		}

		// 로그인이 안되는경우
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
		    response.sendRedirect(request.getContextPath() + "/board");
		    return;
		}
		
		
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(content);
		vo.setUser_id(authUser.getId());
		
		new BoardDao().insert(vo);
		
		
		
		response.sendRedirect(request.getContextPath() + "/board");

	}

}

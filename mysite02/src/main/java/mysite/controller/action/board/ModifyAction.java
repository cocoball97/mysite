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

public class ModifyAction implements Action {

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
		
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
//		System.out.println("modifty 전 id : "+id);
//		System.out.println("modifty 전 title : "+title);
//		System.out.println("modifty 전 content : "+content);
		
		BoardVo vo = new BoardVo();
		vo.setId(Long.parseLong(id));
		vo.setTitle(title);
		vo.setContents(content);
		vo.setUser_id(authUser.getId());		
		
		new BoardDao().modify(vo);
		
//		System.out.println("modifty 후 id : "+vo.getId());
//		System.out.println("modifty 후 title : "+vo.getTitle());
//		System.out.println("modifty 후 content : "+vo.getContents());
		

		response.sendRedirect(request.getContextPath() + "/board?a=view&id="+vo.getId());

	}
}

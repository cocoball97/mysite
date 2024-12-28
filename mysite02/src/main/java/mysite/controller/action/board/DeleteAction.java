package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;

public class DeleteAction implements Action {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 어차피 버튼이 안보여서 고려할 필요가 없음
		
        // 로그인 안된 경우 
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//        if (authUser == null) {
//            response.sendRedirect(request.getContextPath() + "/board");
//            return;
//        }
		
		
        String id = request.getParameter("id");
        new BoardDao().deleteById(Long.parseLong(id));

		response.sendRedirect(request.getContextPath() +"/board");
	}

}

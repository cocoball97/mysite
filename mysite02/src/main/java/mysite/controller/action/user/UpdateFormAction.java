package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.vo.UserVo;

public class UpdateFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// Access Control 
		if(session == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		////////////////////////////////////////////
		
		// update 과제
//		비번 비어있으면 변경하지 않겠다는 의미
//		비번 채워있다면 비번도 변경하겠다는 의미
//		그 다음 리다이렉트
		
		
//		UserVo vo = new UserDao().findById(authUser.getId());
//		request.setAttribute("vo", vo);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/updateform.jsp");
		rd.forward(request, response);
		
	}

}

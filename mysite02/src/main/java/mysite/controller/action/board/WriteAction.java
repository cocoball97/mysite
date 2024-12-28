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
		
		// 세션이 없는 경우
		HttpSession session = request.getSession(true);
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
		vo.setHit(0L);
		vo.setUser_id(authUser.getId());
		
		BoardDao boardDao = new BoardDao();
	
		
		
		if(request.getParameter("g_no")==null) {
			// 새로 글쓰기
			
			Long g_no_max = boardDao.findMaxGNo();
			// 기존보다 큰 숫자 g_no 지정
			vo.setG_no(g_no_max);
			vo.setO_no(1L);
			vo.setDept(0L);
			
			System.out.println("설마 아니지?");
			
		} else {
			// 답글달기			
			
			System.out.println("답글 맞잖아?");
			Long g_no = Long.parseLong(request.getParameter("g_no"));
			System.out.println("g_no값 왜이러는데:"+g_no);
			Long o_no = Long.parseLong(request.getParameter("o_no"));
			Long dept = Long.parseLong(request.getParameter("dept"));
			
			boardDao.updateNo(g_no, o_no, dept);
			
			System.out.println("업데이트 이후 g_no :" +g_no);
			vo.setG_no(g_no);
			vo.setO_no(o_no+1);
			vo.setDept(dept+1);
			
		}
		
		boardDao.insert(vo);
		
		response.sendRedirect(request.getContextPath() + "/board");

	}

}




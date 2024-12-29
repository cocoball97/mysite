package mysite.controller.action.board;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class BoardAction implements Action {

	public static final int write_page = 5;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// 여기도 마찬가지로 보는건 로그인여부와 상관 없음
//		HttpSession session = request.getSession(true);
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		request.setAttribute("authUser", authUser);
		
		BoardDao boardDao = new BoardDao();
		
		List<BoardVo> find_list = boardDao.findAll();
		
		int page_no;
		int count = find_list.size();
		int pageCount = (count/5)+1;
		
		System.out.println("글개수:"+count);
		System.out.println("페이지 수:"+pageCount);
		
		if (request.getParameter("page") != null) {
			page_no = Integer.parseInt(request.getParameter("page"));
		} else {
			page_no = 1;
		}
		
		
		int begin_page = ((page_no-1) / write_page) * write_page + 1;
		int end_page = begin_page + 4;
				
		int pre_page = (begin_page > 1) ? begin_page - 1 : 0;
		int next_page = (end_page < pageCount) ? end_page + 1 : 0;
		
		int write_no = (page_no - 1) * write_page;
		
		
		List<BoardVo> list = boardDao.findBypage(write_no, write_page);
		
		request.setAttribute("list", list);
		request.setAttribute("page_no", page_no);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("begin_page", begin_page);
		request.setAttribute("end_page", end_page);
		request.setAttribute("pre_page", pre_page);
		request.setAttribute("next_page", next_page);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);
	}

}

<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>

					<c:set var="count" value="${fn:length(list) }" />
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>${count-status.index }</td>
							<td style="text-align:left; padding-left:${vo.dept * 10}px"><a href="${pageContext.request.contextPath}/board?a=view&id=${vo.id}&g_no=${vo.g_no}&o_no=${vo.o_no }&dept=${vo.dept }">${vo.title }</a></td>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.reg_date }</td>

							<c:if test="${vo.user_id == sessionScope.authUser.id}">
								<td><a href="${pageContext.request.contextPath}/board?a=delete&id=${vo.id}"> <img src="${pageContext.request.contextPath}/assets/images/recycle.png">
								</a></td>
							</c:if>
						</tr>
					</c:forEach>


				</table>
				<!-- 다음페이지 0이라면 화살표 없애기 jsp는 그림만, action에서 처리 -->
				<!-- pager 추가 -->
				
				<div class="pager">
				    <ul>
				        <li>
				            <c:choose>
				                <c:when test="${pre_page > 0}">
				                    <a href="${pageContext.request.contextPath}/board?page=${pre_page}">◀</a>
				                </c:when>
				                <c:otherwise>
				                    <span>◀</span>
				                </c:otherwise>
				            </c:choose>
				        </li>
				
				        <!-- 페이지 숫자 -->
				        <c:forEach var="i" begin="${begin_page}" end="${end_page}">
				            <li class="${i == page_no ? 'selected' : ''}">
				                <c:if test="${i <= pageCount}">
				                    <a href="${pageContext.request.contextPath}/board?page=${i}">${i}</a>
				                </c:if>
				                <c:if test="${i > pageCount}">
				                    <span>${i}</span>
				                </c:if>
				            </li>
				        </c:forEach>
				
				        <li>
				            <c:choose>
				                <c:when test="${next_page > 0}">
				                    <a href="${pageContext.request.contextPath}/board?page=${next_page}">▶</a>
				                </c:when>
				                <c:otherwise>
				                    <span>▶</span>
				                </c:otherwise>
				            </c:choose>
				        </li>
				    </ul>
				</div>
				
				<!-- pager 추가 -->

				<div class="bottom">
					<c:if test="${null != sessionScope.authUser.id}">
						<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
					</c:if>
				</div>

			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>
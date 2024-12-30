package mysite.security;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		// @AuthUser가 안 붙어 있는 경우
		if(authUser ==null) {
			return false;
		}
		
		// 파라미터 타입이 UserVo가 아니면
		if(!parameter.getParameterType().equals(UserVo.class)) {
			return false;
		};
		
		//같은지 확인 안하나? 
		
		return true;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// 처리 가능한 매개변수가 없다면
		if(!supportsParameter(parameter)) {
			// unresolved : 처리 불가한 값
			return WebArgumentResolver.UNRESOLVED;
		}
		
		HttpServletRequest request =  (HttpServletRequest)webRequest.getNativeRequest();
		HttpSession session = request.getSession();
		
		return session.getAttribute("authUser");
	}

}

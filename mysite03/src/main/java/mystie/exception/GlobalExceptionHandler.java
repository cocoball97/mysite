package mystie.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static Log log = LogFactory.getLog(GlobalExceptionHandler.class);
	
	// 컨트롤러 메서드와 거의 동일하게 취급
	@ExceptionHandler(Exception.class)
	public String handler(Model model, Exception e) {
		// 1 .로깅(logging)
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		log.error(errors.toString());
		
		// 2. 사과 페이지(종료)
		model.addAttribute("errors", errors.toString());
		return "error/execption";
	}

}
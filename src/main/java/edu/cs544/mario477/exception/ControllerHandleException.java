package edu.cs544.mario477.exception;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Component
@ControllerAdvice
public class ControllerHandleException{

    public static final String DEFAULT_ERROR_VIEW = "errors/error";
    public static final String DEFAULT_ACCESS_DENIED = "errors/forbidden";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception ex) throws Exception {

        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
            throw ex;
        }

        ModelAndView mav = new ModelAndView();

        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("url", request.getRequestURI());
        mav.setViewName(DEFAULT_ERROR_VIEW);

        return mav;
    }
}

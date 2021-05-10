package com.springcloud.service.exception;

import com.springcloud.service.common.BaseResult;
import com.springcloud.service.constant.Code;
import org.springframework.http.HttpRequest;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;

/**
 *
 * 全局异常处理
 * 这种异常处理方式一般用来处理应用级别的异常，一些容器级别的错误就处理不了，比如 Filter(Servlet容器的) 中抛出的异常
 *
 * @RestControllerAdvice 包含了 @ControllerAdvice + @ResponseBody
 * 指定 @ResponseBody 后，返回的数据不会有 html 页面，而是json数据
 *
 * 动态错误资源，动态优于静态，404.html优于 4xx.html
 */
@ControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public BaseResult handleTokenException(ConstraintViolationException e) {
//        log.error(e.getMessage());
        e.printStackTrace();
        StringBuilder sb = new StringBuilder();
        Iterator<ConstraintViolation<?>> iterator = e.getConstraintViolations().iterator();
        while(iterator.hasNext()){
            sb.append(iterator.next().getMessage());
        }
        return new BaseResult(Code.PARAMETER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleDefaultErrorPage(Exception e) {
        //设置要展示的页面名称
        ModelAndView mav = new ModelAndView("error");
        //页面属性设置，可以在 html 页面中根据对应的 属性名 获取 设置的属性值
        mav.addObject("exception", e);
        mav.addObject("url", "httpRequest.getURI()");
        System.out.println(mav.toString());
        return mav;
    }

    /*@ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResult handleDefaultException(Exception be) {
        be.printStackTrace();
        return new BaseResult(Code.UNKNOWN_ABNORMAL.getCode(), Code.UNKNOWN_ABNORMAL.getMsg());
    }*/

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public BaseResult handlerMethodArgumentNotValidException(Exception e){
//        logger.error(e);
        String defaultMessage = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        e.printStackTrace();
        return new BaseResult(Code.VALID_ERROR.getCode(),defaultMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public BaseResult handlerHttpMessageNotReadableException(Exception e){
//        logger.info(e);
        return new BaseResult(Code.JSON_TRANSFER_ERROR.getCode(),Code.JSON_TRANSFER_ERROR.getMsg());
    }

}

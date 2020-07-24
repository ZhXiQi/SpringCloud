package com.springcloud.service.exception;

import com.springcloud.service.common.BaseResult;
import com.springcloud.service.constant.Code;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;

/**
 *
 * 全局异常处理
 */
@RestControllerAdvice
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
    @ResponseBody
    public BaseResult handleDefaultException(Exception be) {
//        logger.error(be);
        be.printStackTrace();
        return new BaseResult(Code.UNKNOWN_ABNORMAL.getCode(), Code.UNKNOWN_ABNORMAL.getMsg());
    }

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

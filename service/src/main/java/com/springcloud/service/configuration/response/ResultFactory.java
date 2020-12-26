package com.springcloud.service.configuration.response;


import com.springcloud.service.constant.Code;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019-07-02 19:33
 */
public class ResultFactory {

    public static Result success(){
        return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMsg());
    }

    public static Result success(Object data, Code resultCode){
        Result result = new Result(resultCode.getCode(), resultCode.getMsg(), data);
        return result;
    }

    public static Result success(Object data){
        return new Result(Code.SUCCESS.getCode(),Code.SUCCESS.getMsg(),data);
    }

    public static Result error(){
        return new Result(Code.ERROR.getCode(),Code.ERROR.getMsg());
    }
    public static Result error(Code resultCode){
        return new Result(resultCode.getCode(),resultCode.getMsg());
    }

    public static Result error(Code resultCode, Object data){
        Result result = new Result(resultCode.getCode(), resultCode.getMsg(), data);
        return result;
    }

}

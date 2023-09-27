package com.crazypug.core.bean;


public class Result<T> {

    private T data;

    private int code;


    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T>Result<T>  ok(){
      return result(CodeEnum.OK);
    }

    public static <T>Result<T>  ok(T data){
        Result<T> result = result(CodeEnum.OK);
        result.setData(data);
        return result;
    }
//业务
    public static <T>Result<T>  accepted(){
        return result(CodeEnum.ACCEPTED);
    }
    public static <T>Result<T>  accepted(String message){
        return result(CodeEnum.ACCEPTED,message);
    }
    public static <T>Result<T>  badRequest(){
        return result(CodeEnum.BAD_REQUEST);
    }
    public static <T>Result<T>  badRequest(String message){
        return result(CodeEnum.BAD_REQUEST,message);
    }
    public static <T>Result<T>  unauthorized(){
        return result(CodeEnum.UNAUTHORIZED);
    }
    public static <T>Result<T>  unauthorized(String message){
        return result(CodeEnum.UNAUTHORIZED,message);
    }
    public static <T>Result<T>  internalServerError(){
        return result(CodeEnum.INTERNAL_SERVER_ERROR);
    }

    public static <T>Result<T>  internalServerError(String message){
        return result(CodeEnum.INTERNAL_SERVER_ERROR,message);
    }
    public static <T>Result<T> result(CodeEnum codeEnum,String message){
        Result<T> result = new Result<>();
        result.setCode(codeEnum.code);
        if (message!=null){
            result.setMessage(message);
        }else {
            result.setMessage(codeEnum.message);
        }
        return result;
    }
    public static <T>Result<T> result(CodeEnum codeEnum){
        return result(codeEnum,null);
    }



}

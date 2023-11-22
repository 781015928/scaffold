package com.crazypug.web.core.bean;

public enum CodeEnum {
    OK(200,"ok"),
    ACCEPTED(202,"accepted"),

    BAD_REQUEST(400,"accepted"),

    UNAUTHORIZED(401,"Unauthorized"),

    INTERNAL_SERVER_ERROR(500,"Internal Server Error"),
    ;


    public int code;
    public String message;
    CodeEnum(int code,String message){
        this.code=code;
        this.message=message;
    }


}

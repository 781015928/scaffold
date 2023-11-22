package com.crazypug.web.core.exception;


/**
 * test webhook
 */
public class Exceptions {



    public static void returnThrowBadRequest(String message, Throwable cause) {
        throw new BadRequestException(message, cause);
    }

    public static void returnThrowBadRequest(String message) {
        throw new BadRequestException(message);
    }

    public static void returnThrowBadBusiness(String message, Throwable cause) {
        throw new BusinessException(message, cause);
    }

    public static void returnThrowBadBusiness(String message) {
        throw new BusinessException(message);
    }

    public static void returnThrowServerError(String message, Throwable cause) {
        throw new ServerErrorException(message, cause);
    }

    public static void returnThrowServerError(String message) {
        throw new ServerErrorException(message);
    }
    public static void returnThrowUnauthorized(String message, Throwable cause) {
        throw new UnauthorizedException(message, cause);
    }

    public static void returnThrowUnauthorized(String message) {
        throw new UnauthorizedException(message);
    }

}

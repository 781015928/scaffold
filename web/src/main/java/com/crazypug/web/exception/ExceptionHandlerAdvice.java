package com.crazypug.web.exception;


import com.crazypug.core.bean.Result;
import com.crazypug.core.exception.BadRequestException;
import com.crazypug.core.exception.BusinessException;
import com.crazypug.core.exception.ServerErrorException;
import com.crazypug.core.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ConditionalOnProperty(name = "crazypug.exception-handler.enable", havingValue = "true")
@RestControllerAdvice(annotations = {Controller.class, RestController.class})
public class ExceptionHandlerAdvice {

   private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(BadRequestException.class)
    public Result<String> handleException(BadRequestException ex){
       return Result.badRequest(ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result<String> handleException(BusinessException ex){
        return Result.accepted(ex.getMessage());
    }

    @ExceptionHandler({ServerErrorException.class,Throwable.class})
    public Result<String> handleException(Throwable ex){
        log.error(ex.getMessage(),ex);
        return Result.internalServerError(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Result<String> handleException(UnauthorizedException ex){

        return Result.unauthorized(ex.getMessage());
    }



    public Result<String> pareException(Throwable ex){
        if (ex instanceof BadRequestException){
            return handleException((BadRequestException)ex);
        }else  if (ex instanceof BusinessException){
            return handleException((BusinessException)ex);
        }else  if (ex instanceof UnauthorizedException){
            return handleException((UnauthorizedException)ex);
        }else  {
            return handleException(ex);
        }

    }



}

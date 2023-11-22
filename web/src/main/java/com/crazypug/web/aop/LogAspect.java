package com.crazypug.web.aop;

import com.crazypug.web.core.bean.Result;
import com.crazypug.web.config.CrazyPugConfig;
import com.crazypug.web.exception.ExceptionHandlerAdvice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

@Aspect
public class LogAspect  {

    private ObjectMapper objectMapper=new ObjectMapper();
    private Set<Method> whiteList=new HashSet<>();
    private Map<Method,LogIgnore> blackListMethod=new HashMap<Method,LogIgnore>();

    @Autowired(required = false)
    private CrazyPugConfig crazyPugConfig;


    @Autowired(required = false)
    private ExceptionHandlerAdvice exceptionHandlerAdvice;

    private final static Logger logger         = LoggerFactory.getLogger(LogAspect.class);
    /** 换行符 */
    private static final String LINE_SEPARATOR = System.lineSeparator();

    /** 以自定义 @WebLog 注解为切点 */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.PostMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.PutMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.PatchMapping)")
    public void mappings() {


    }

    @Around("mappings()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();


        LogIgnore logIgnore=null;

        if (!whiteList.contains(method)){

            logIgnore=  blackListMethod.get(method);

            if (logIgnore==null){

                logIgnore = method.getAnnotation(LogIgnore.class);

                if (logIgnore!=null){

                    blackListMethod.put(method,logIgnore);

                }else {

                    whiteList.add(method);

                }


            }

        }

        boolean logRequest= logIgnore==null||!logIgnore.ignorelevel().ignoreRequest;

        boolean logInArgs= logIgnore==null||!logIgnore.ignorelevel().ignoreInArgs;

        boolean logOutArgs= logIgnore==null||!logIgnore.ignorelevel().ignoreOutArgs;



        if (logRequest){
            logger.info("========================================== request_start ==========================================");
            logger.info("URL            : {}", request.getRequestURL().toString());
            logger.info("HTTP Method    : {}", request.getMethod());
            String traceIdName = crazyPugConfig.mdc.traceIdName;
            if (traceIdName != null) {
                String traceId = MDC.get(traceIdName);
                logger.info("Request MDC    : {}={}", traceIdName,traceId);
            }
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames.hasMoreElements()){
                String headerName = headerNames.nextElement();
                logger.info("Http Header    : {}={}", headerName,request.getHeader(headerName));
            }

            while (headerNames.hasMoreElements()){
                String headerName = headerNames.nextElement();
                logger.info("                 {}={}", headerName,request.getHeader(headerName));
            }
            logger.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            logger.info("IP             : {}", request.getRemoteAddr());

        }
        long startTime = System.currentTimeMillis();

        if (logInArgs){
            // 打印请求入参
            logger.info("Request Args   : {}", toJson(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (logOutArgs){
                // 打印请求入参
                logger.info("Response Args  : {}", toJson(result));

            }

            return result;
        }catch (Throwable ex){
            if (exceptionHandlerAdvice!=null){

                Result<String> stringResult = exceptionHandlerAdvice.pareException(ex);
                if (logOutArgs){
                    // 打印请求入参
                    logger.info("Response Args  : {}", toJson(stringResult));
                }
            }else {
                if (logOutArgs){
                    // 打印请求入参
                    logger.info("Response Args  : {}", ex.getMessage());
                }

            }


            throw ex;
        }finally {

            if (logRequest){

                logger.info("Time-Consuming : {} ms", System.currentTimeMillis()-startTime);
                logger.info("=========================================== request_end ===========================================" + LINE_SEPARATOR);
            }
        }



    }

    private String toJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return String.format( "json encode error ,%s",e.getMessage());
        }
    }

}
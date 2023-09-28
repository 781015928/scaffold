package com.crazypug.web.aop;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.crazypug.web.config.CrazyPugConfig;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MDCInterceptor implements HandlerInterceptor {


    private  CrazyPugConfig  crazyPugConfig;

    public MDCInterceptor(CrazyPugConfig crazyPugConfig) {
        this.crazyPugConfig = crazyPugConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String traceId = request.getHeader(crazyPugConfig.mdc.traceIdName);
        if (traceId != null) {
            MDC.put(crazyPugConfig.mdc.traceIdName, traceId);
        }


        traceId = setTraceIdIfAbsent();

        response.setHeader(crazyPugConfig.mdc.traceIdName, traceId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) {


        remove();
    }


    public  String setTraceIdIfAbsent() {
        String traceId=MDC.get(crazyPugConfig.mdc.traceIdName);
        if (traceId== null) {
            traceId  = generateTraceId();
            MDC.put(crazyPugConfig.mdc.traceIdName, traceId);
        }


        return traceId;
    }

    public  void remove() {
        MDC.remove(crazyPugConfig.mdc.traceIdName);
    }


    public static String generateTraceId() {
        final Random ng = ThreadLocalRandom.current();

        final byte[] randomBytes = new byte[16];
        ng.nextBytes(randomBytes);

        randomBytes[6] &= 0x0f; /* clear version */
        randomBytes[6] |= 0x40; /* set to version 4 */
        randomBytes[8] &= 0x3f; /* clear variant */
        randomBytes[8] |= 0x80; /* set to IETF variant */


        long msb = 0;
        long lsb = 0;

        for (int i = 0; i < 8; i++) {
            msb = (msb << 8) | (randomBytes[i] & 0xff);
        }
        for (int i = 8; i < 16; i++) {
            lsb = (lsb << 8) | (randomBytes[i] & 0xff);
        }

        final StringBuilder builder = StrUtil.builder(36);
        // time_low
        builder.append(digits(msb >> 32, 8));

        // time_mid
        builder.append(digits(msb >> 16, 4));

        // time_high_and_version
        builder.append(digits(msb, 4));

        // variant_and_sequence
        builder.append(digits(lsb >> 48, 4));

        // node
        builder.append(digits(lsb, 12));

        return builder.toString();
    }

    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }
}
package com.crazypug.web.aop;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MDCInterceptor implements HandlerInterceptor {

    private static final String TRACE_ID = "X-TRACE-ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String traceId = request.getHeader(TRACE_ID);
        if (traceId != null) {
            MDC.put(TRACE_ID, traceId);
        }


        setTraceIdIfAbsent();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) {

        String traceId = MDC.get(TRACE_ID);
        response.addHeader(TRACE_ID, traceId);
        remove();
    }


    public static void setTraceIdIfAbsent() {
        if (MDC.get(TRACE_ID) == null) {
            MDC.put(TRACE_ID, generateTraceId());
        }
    }

    public static void remove() {
        MDC.remove(TRACE_ID);
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
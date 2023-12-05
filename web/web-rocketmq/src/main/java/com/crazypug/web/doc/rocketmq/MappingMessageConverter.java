package com.crazypug.web.doc.rocketmq;

import cn.hutool.core.util.IdUtil;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.DefaultRocketMQListenerContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class MappingMessageConverter extends MappingJackson2MessageConverter implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof RocketMQTemplate) {
            ((RocketMQTemplate) bean).setMessageConverter(this);
            return bean;
        }
        if (bean instanceof DefaultRocketMQListenerContainer) {
            ((DefaultRocketMQListenerContainer) bean).setMessageConverter(this);
            return bean;
        }


        return bean;
    }

    @Value("${rocketmq.message-gmid-key:gm-id}")
    private String GOABAL_MESSAGE_ID_KEY;

    private static final ThreadLocal<String> GM_ID = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return IdUtil.fastSimpleUUID();
        }
    };

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        String gmid = message.getHeaders().get(GOABAL_MESSAGE_ID_KEY, String.class);
        if (gmid != null) {
            GM_ID.set(gmid);
        }
        return super.convertFromInternal(message, targetClass, conversionHint);
    }


    @Override
    protected Object convertToInternal(Object payload, MessageHeaders headers, Object conversionHint) {
        String gmid = GM_ID.get();
        Map<String, Object> header = headers.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        header.put(GOABAL_MESSAGE_ID_KEY, gmid);
        MessageHeaders messageHeaders = new MessageHeaders(headers);
        return super.convertToInternal(payload, messageHeaders, conversionHint);
    }
}

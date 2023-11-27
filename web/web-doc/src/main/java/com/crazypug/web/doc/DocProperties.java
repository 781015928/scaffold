package com.crazypug.web.doc;

import com.github.xiaoymin.knife4j.spring.configuration.Knife4jInfoProperties;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Data
@Primary
@EqualsAndHashCode(callSuper=false)
@ConfigurationProperties(prefix = "crazypug.doc")
public class DocProperties extends Knife4jProperties {

    private DocInfoProperties info;
    private boolean enable = false;
    @Override
    public Knife4jInfoProperties getOpenapi() {
        return info;
    }
}

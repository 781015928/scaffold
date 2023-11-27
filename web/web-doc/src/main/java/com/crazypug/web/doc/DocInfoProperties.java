package com.crazypug.web.doc;

import com.github.xiaoymin.knife4j.spring.configuration.Knife4jInfoProperties;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Data
@Primary
@ConfigurationProperties(prefix = "crazypug.doc.info")
public class DocInfoProperties extends Knife4jInfoProperties {





}

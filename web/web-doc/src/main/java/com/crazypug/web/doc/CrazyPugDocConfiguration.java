package com.crazypug.web.doc;

import com.github.xiaoymin.knife4j.core.conf.GlobalConstants;
import com.github.xiaoymin.knife4j.core.enums.OpenAPILanguageEnums;
import com.github.xiaoymin.knife4j.core.extend.OpenApiExtendSetting;
import com.github.xiaoymin.knife4j.extend.filter.basic.ServletSecurityBasicAuthFilter;
import com.github.xiaoymin.knife4j.spring.common.bean.Knife4jDocketAutoRegistry;
import com.github.xiaoymin.knife4j.spring.common.bean.Knife4jI18nServiceModelToSwagger2MapperImpl;
import com.github.xiaoymin.knife4j.spring.configuration.*;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import com.github.xiaoymin.knife4j.spring.filter.ProductionSecurityFilter;
import com.github.xiaoymin.knife4j.spring.model.docket.Knife4jAuthInfoProperties;
import com.github.xiaoymin.knife4j.spring.util.EnvironmentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
import springfox.documentation.swagger2.mappers.*;

import java.util.Arrays;
import java.util.Locale;

@Configuration
@ConditionalOnProperty(name = "crazypug.doc.enable", havingValue = "true")
@EnableConfigurationProperties({DocProperties.class, DocInfoProperties.class, Knife4jHttpBasic.class, Knife4jSetting.class, Knife4jAuthInfoProperties.class})
public class CrazyPugDocConfiguration {

    private final Environment environment;




    Logger logger = LoggerFactory.getLogger(com.github.xiaoymin.knife4j.spring.configuration.Knife4jAutoConfiguration.class);

    public CrazyPugDocConfiguration(Environment environment) {
        this.environment = environment;
    }

    /**
     * Write an internal class. This class will be loaded only when `knife4j.enable` = true, without any processing
     * https://github.com/xiaoymin/swagger-bootstrap-ui/issues/394
     *
     * @since v4.0.0
     */
    @ComponentScan(basePackages = {
            "com.github.xiaoymin.knife4j.spring.plugin",
            "com.github.xiaoymin.knife4j.spring.common"
    })
    @EnableSwagger2WebMvc
    public class Knife4jEnhanceAutoConfiguration {

        /**
         * Auto Register Springfox Docket Bean Information to Spring Context
         *
         * @param knife4jProperties Knife4j properties
         * @return knife4jDocketAutoRegistry
         */
        @Bean
        @Qualifier("knife4jDocketAutoRegistry")
        public Knife4jDocketAutoRegistry knife4jDocketAutoRegistry(Knife4jProperties knife4jProperties, OpenApiExtensionResolver openApiExtensionResolver) {
            return new Knife4jDocketAutoRegistry(knife4jProperties, openApiExtensionResolver);
        }

        /**
         * Register Primary Bean with ServiceModelToSwagger2Mapper to Support i18n
         *
         * @param knife4jProperties      Knife4j properties
         * @param messageSource          i18n MessageSource
         * @param modelMapper            modelMapper
         * @param parameterMapper        parameterMapper
         * @param securityMapper         securityMapper
         * @param licenseMapper          licenseMapper
         * @param vendorExtensionsMapper vendorExtensionsMapper
         * @return ServiceModelToSwagger2Mapper
         */
        @Bean
        @ConditionalOnBean(value = MessageSource.class)
        @Qualifier("ServiceModelToSwagger2Mapper")
        @Primary
        public Knife4jI18nServiceModelToSwagger2MapperImpl knife4jI18nServiceModelToSwagger2Mapper(DocProperties knife4jProperties, MessageSource messageSource, ModelMapper modelMapper,
                                                                                                   ParameterMapper parameterMapper, SecurityMapper securityMapper, LicenseMapper licenseMapper,
                                                                                                   VendorExtensionsMapper vendorExtensionsMapper) {
            // fixed npe
            String language = OpenAPILanguageEnums.ZH_CN.getValue();
            if (knife4jProperties.getSetting() != null) {
                language = knife4jProperties.getSetting().getLanguage().getValue();
            }
            Locale locale = Locale.forLanguageTag(language);
            return new Knife4jI18nServiceModelToSwagger2MapperImpl(messageSource, locale, modelMapper, parameterMapper, securityMapper, licenseMapper, vendorExtensionsMapper);
        }
    }

    /**
     * Configuration CorsFilter
     *
     * @return
     * @since 2.0.4
     */
    @Bean("knife4jCorsFilter")
    @ConditionalOnMissingBean(CorsFilter.class)
    @ConditionalOnProperty(name = "knife4j.cors", havingValue = "true")
    public CorsFilter corsFilter() {
        logger.info("init CorsFilter...");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        // corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
        corsConfiguration.setMaxAge(GlobalConstants.CORS_MAX_AGE);
        // 匹配所有API
        source.registerCorsConfiguration("/**", corsConfiguration);
        CorsFilter corsFilter = new CorsFilter(source);
        return corsFilter;
    }

    /**
     * Auto register enhance Bean to process Knife4j function
     *
     * @param knife4jProperties enhance properties
     * @return openapi-extension
     */
    @Bean(initMethod = "start")
    @ConditionalOnMissingBean(OpenApiExtensionResolver.class)
    @ConditionalOnProperty(name = "crazypug.doc.enable", havingValue = "true")
    public OpenApiExtensionResolver markdownResolver(DocProperties knife4jProperties) {
        Knife4jSetting setting = knife4jProperties.getSetting() != null ? knife4jProperties.getSetting() : new Knife4jSetting();
        OpenApiExtendSetting extendSetting = new OpenApiExtendSetting();
        BeanUtils.copyProperties(setting, extendSetting);
        extendSetting.setLanguage(setting.getLanguage().getValue());
        return new OpenApiExtensionResolver(extendSetting, knife4jProperties.getDocuments());
    }

    /**
     * Security with Basic Http
     *
     * @param knife4jProperties Basic Properties
     * @return BasicAuthFilter
     */
    @Bean
    @ConditionalOnMissingBean(ServletSecurityBasicAuthFilter.class)
    @ConditionalOnProperty(name = "knife4j.basic.enable", havingValue = "true")
    public ServletSecurityBasicAuthFilter securityBasicAuthFilter(DocProperties knife4jProperties) {
        ServletSecurityBasicAuthFilter authFilter = new ServletSecurityBasicAuthFilter();
        if (knife4jProperties == null) {
            authFilter.setEnableBasicAuth(EnvironmentUtils.resolveBool(environment, "knife4j.basic.enable", Boolean.FALSE));
            authFilter.setUserName(EnvironmentUtils.resolveString(environment, "knife4j.basic.username", GlobalConstants.BASIC_DEFAULT_USERNAME));
            authFilter.setPassword(EnvironmentUtils.resolveString(environment, "knife4j.basic.password", GlobalConstants.BASIC_DEFAULT_PASSWORD));
        } else {
            // 判断非空
            if (knife4jProperties.getBasic() == null) {
                authFilter.setEnableBasicAuth(Boolean.FALSE);
                authFilter.setUserName(GlobalConstants.BASIC_DEFAULT_USERNAME);
                authFilter.setPassword(GlobalConstants.BASIC_DEFAULT_PASSWORD);
            } else {
                authFilter.setEnableBasicAuth(knife4jProperties.getBasic().isEnable());
                authFilter.setUserName(knife4jProperties.getBasic().getUsername());
                authFilter.setPassword(knife4jProperties.getBasic().getPassword());
                authFilter.addRule(knife4jProperties.getBasic().getInclude());
            }
        }
        return authFilter;
    }

    @Bean
    @ConditionalOnMissingBean(ProductionSecurityFilter.class)
    @ConditionalOnProperty(name = "knife4j.production", havingValue = "true")
    public ProductionSecurityFilter productionSecurityFilter(Knife4jProperties knife4jProperties) {
        Knife4jSetting setting = knife4jProperties.getSetting() != null ? knife4jProperties.getSetting() : new Knife4jSetting();
        ProductionSecurityFilter p = null;
        if (knife4jProperties == null) {
            int customCode = EnvironmentUtils.resolveInt(environment, "knife4j.setting.custom-code", 200);
            boolean prod = EnvironmentUtils.resolveBool(environment, "knife4j.production", Boolean.FALSE);
            p = new ProductionSecurityFilter(prod, customCode);
        } else {
            p = new ProductionSecurityFilter(knife4jProperties.isProduction(), setting.getCustomCode());
        }
        return p;
    }

}

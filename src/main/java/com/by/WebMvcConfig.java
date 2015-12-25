package com.by;

import com.by.converter.StringToCalendar;
import com.by.converter.StringToCouponAdminStateEnum;
import com.by.converter.StringToDuplicateEnum;
import com.by.converter.StringToValidEnumConverter;
import com.by.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/META-INF/resources/",
            "classpath:/resources/", "classpath:/static/", "classpath:/public/"};

    private static final String[] RESOURCE_LOCATIONS = {"classpath:/src/main/app/", "classpath:/src/main/app/"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor()).addPathPatterns("/api/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        }

        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS).setCachePeriod(0);
        }

        if (!registry.hasMappingForPattern("/app/**")) {
            registry.addResourceHandler("/app/**").addResourceLocations(RESOURCE_LOCATIONS).setCachePeriod(0);
        }
    }

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {
        formatterRegistry.addConverter(stringToValidEnumConverter());
        formatterRegistry.addConverter(stringToCalendar());
        formatterRegistry.addConverter(stringToCouponAdminStateEnum());
        formatterRegistry.addConverter(stringToDuplicateEnum());
    }

    @Bean
    public StringToValidEnumConverter stringToValidEnumConverter() {
        return new StringToValidEnumConverter();
    }

    @Bean
    public StringToCalendar stringToCalendar() {
        return new StringToCalendar();
    }

    @Bean
    public StringToCouponAdminStateEnum stringToCouponAdminStateEnum() {
        return new StringToCouponAdminStateEnum();
    }

    @Bean
    public StringToDuplicateEnum stringToDuplicateEnum() {
        return new StringToDuplicateEnum();
    }

    @Bean
    public Filter hiddenHttpMethodFilter() {
        HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
        return filter;
    }
}

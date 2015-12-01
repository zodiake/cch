package com.by;

import com.by.converter.StringToMemberConverter;
import com.by.converter.StringToParkingCouponConverter;
import com.by.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor()).addPathPatterns("/api/**");
    }

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {
        formatterRegistry.addConverter(stringToMemberConverter());
        formatterRegistry.addConverter(stringToParkingCouponConverter());
    }

    @Bean
    public StringToMemberConverter stringToMemberConverter() {
        return new StringToMemberConverter();
    }

    @Bean
    public StringToParkingCouponConverter stringToParkingCouponConverter() {
        return new StringToParkingCouponConverter();
    }
}

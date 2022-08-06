package com.david.javamoim.config;

import com.david.javamoim.security.JwtTokenProvider;
import com.david.javamoim.support.AccessHandlerMethodArgumentResolver;
import com.david.javamoim.support.MemberAuthenticationFilter;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AccessHandlerMethodArgumentResolver());
    }

    @Bean
    public FilterRegistrationBean<MemberAuthenticationFilter> memberAuthenticationFilter(
            JwtTokenProvider jwtTokenProvider) {
        FilterRegistrationBean<MemberAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MemberAuthenticationFilter(jwtTokenProvider));
        registrationBean.addUrlPatterns("/api/members/*");
        return registrationBean;
    }
}

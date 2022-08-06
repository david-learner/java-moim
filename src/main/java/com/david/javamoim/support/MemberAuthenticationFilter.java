package com.david.javamoim.support;

import com.david.javamoim.security.JwtTokenProvider;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

public class MemberAuthenticationFilter implements Filter {

    private static final String INVALID_JWT_TOKEN_TYPE_MESSAGE = "토큰 타입이 일치하지 않습니다.";
    private final JwtTokenProvider jwtTokenProvider;
    private static final String TOKEN_TYPE_SEPARATOR = " ";
    private static final String JWT_TOKEN_TYPE = "Bearer";

    public MemberAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String[] authorizationTokens = httpRequest.getHeader(HttpHeaders.AUTHORIZATION).split(TOKEN_TYPE_SEPARATOR);
        String tokenType = authorizationTokens[0];
        String accessToken = authorizationTokens[1];
        if (!tokenType.equals(JWT_TOKEN_TYPE)) {
            throw new IllegalArgumentException(INVALID_JWT_TOKEN_TYPE_MESSAGE);
        }
        String memberId = jwtTokenProvider.extractClaimValue(accessToken, MemberAuthentication.MEMBER_ID_KEY);
        httpRequest.setAttribute(MemberAuthentication.MEMBER_ID_KEY, memberId);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

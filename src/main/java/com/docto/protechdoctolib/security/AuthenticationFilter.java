package com.docto.protechdoctolib.security;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.el.stream.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.security.config.web.server.SecurityWebFiltersOrder.AUTHORIZATION;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    AuthenticationFilter(final RequestMatcher requiresAuth){
        super(requiresAuth);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        Optional tokenParam = java.util.Optional.ofNullable(HttpServletRequest.getHeader(AUTHORIZATION)); //Authorization: Bearer TOKEN
        String token = HttpServletRequest.getHeader(AUTHORIZATION);
        token= StringUtils.remove(token, "Bearer").trim();
        Authentication requestAuthentication = new UsernamePasswordAuthenticationToken(token, token);
        return getAuthenticationManager().authenticate(requestAuthentication);
    }
}

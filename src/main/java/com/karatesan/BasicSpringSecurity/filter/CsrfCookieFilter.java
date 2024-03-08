package com.karatesan.BasicSpringSecurity.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CsrfCookieFilter extends OncePerRequestFilter {

    //ten customowy filter ma przechwycic request i wrzuca do responsa header zwiazany z csrf jezeli go tam nie ma

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if(csrfToken.getHeaderName() != null){
            response.setHeader(csrfToken.getHeaderName(),csrfToken.getToken());
        }
        filterChain.doFilter(request,response);

    }
}

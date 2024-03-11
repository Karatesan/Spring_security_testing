package com.karatesan.BasicSpringSecurity.filter;

import jakarta.servlet.*;

import java.io.IOException;
import java.util.logging.Logger;

public class AuthoritiesLoggingAtFilter implements Filter {

   private final Logger Log = Logger.getLogger(AuthoritiesLoggingAtFilter.class.getName());
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Log.info("Authentication Validation is in progress");
        chain.doFilter(request,response);
    }
}

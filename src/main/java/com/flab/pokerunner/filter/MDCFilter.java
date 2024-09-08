package com.flab.pokerunner.filter;

import org.slf4j.MDC;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

public class MDCFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String requestUri = httpRequest.getRequestURI();
            String uniqueId = UUID.randomUUID().toString();
            String truncatedUniqueId = uniqueId.split("-", 2)[0];

            MDC.put("URI", requestUri);
            MDC.put("ID", truncatedUniqueId);

            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
package com.hafiez.cards_assignment.config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger("API_LOGGER");

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request, 1024 * 1024);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper(response);

        long start = System.currentTimeMillis();
        try {
            chain.doFilter(req, res);
        } finally {
            long timeMs = System.currentTimeMillis() - start;

            String reqBody = new String(req.getContentAsByteArray(), StandardCharsets.UTF_8);
            String resBody = new String(res.getContentAsByteArray(), StandardCharsets.UTF_8);
            String query = req.getQueryString() == null ? "" : "?" + req.getQueryString();

            log.info("REQUEST  | {} {}{} | body={}",
                    req.getMethod(), req.getRequestURI(), query, reqBody);
            log.info("RESPONSE | {} {} | status={} | time={}ms | body={}",
                    req.getMethod(), req.getRequestURI(), res.getStatus(), timeMs, resBody);

            res.copyBodyToResponse();
        }
    }
}

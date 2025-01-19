package com.core.Core.Service.config;
import com.core.Core.Service.data_submission.audit.AuditLog;
import com.core.Core.Service.data_submission.audit.AuditRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter("/*")
@RequiredArgsConstructor
public class AccessLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AccessLoggingFilter.class);
    private final AuditRepository repository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Capture request details
        String ipAddress = request.getRemoteAddr();
        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();
        long startTime = System.currentTimeMillis();

        // Proceed with the request
        chain.doFilter(request, response);

        // After processing, log details
        long duration = System.currentTimeMillis() - startTime;
        int status = httpResponse.getStatus();

        // Create AccessLog entry
        AuditLog log = new AuditLog();
        log.setIpAddress(ipAddress);
        log.setMethod(method);
        log.setUri(uri);
        log.setDuration(duration);
        log.setStatus(status);
        log.setTimestamp(LocalDateTime.now());

        // Save log to database
        repository.save(log);

        logger.info("Access Attempt: IP = {}, Method = {}, URI = {}, Duration = {} ms, Status = {}",
                ipAddress, method, uri, duration, status);
    }

    @Override
    public void destroy() {

    }
}

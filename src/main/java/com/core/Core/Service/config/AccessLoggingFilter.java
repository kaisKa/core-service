package com.core.Core.Service.config;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter("/*")
public class AccessLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AccessLoggingFilter.class);

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

        // Proceed with request processing
        chain.doFilter(request, response);

        // Log the request after processing
        long duration = System.currentTimeMillis() - startTime;
        logger.info("Access Attempt: IP = {}, Method = {}, URI = {}, Duration = {} ms, Status = {}",
                ipAddress, method, uri, duration, httpResponse.getStatus());
    }

    @Override
    public void destroy() {
        // Cleanup resources (if needed)
    }
}

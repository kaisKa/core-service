//package com.core.Core.Service.data_submission.auth;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//@WebFilter("/*")
//public class AccessLoggingFilter implements Filter {
//
//    @Autowired
//    private AccessLogRepository accessLogRepository;
//
//    private static final Logger logger = LoggerFactory.getLogger(AccessLoggingFilter.class);
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        // Capture request details
//        String ipAddress = request.getRemoteAddr();
//        String method = httpRequest.getMethod();
//        String uri = httpRequest.getRequestURI();
//        long startTime = System.currentTimeMillis();
//
//        // Proceed with the request
//        chain.doFilter(request, response);
//
//        // After processing, log details
//        long duration = System.currentTimeMillis() - startTime;
//        int status = httpResponse.getStatus();
//
//        // Create AccessLog entry
//        AccessLog log = new AccessLog();
//        log.setIpAddress(ipAddress);
//        log.setMethod(method);
//        log.setUri(uri);
//        log.setDuration(duration);
//        log.setStatus(status);
//        log.setTimestamp(LocalDateTime.now());
//
//        // Save log to database
//        accessLogRepository.save(log);
//
//        logger.info("Access Attempt: IP = {}, Method = {}, URI = {}, Duration = {} ms, Status = {}",
//                ipAddress, method, uri, duration, status);
//    }
//}

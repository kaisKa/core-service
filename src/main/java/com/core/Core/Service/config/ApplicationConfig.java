package com.core.Core.Service.config;

import com.core.Core.Service.data_submission.audit.AuditRepository;
import com.core.Core.Service.data_submission.costomer.CustomerRepository;
import com.core.Core.Service.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final CustomerRepository repository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username).orElseThrow(() -> new NotFoundException("Email Not Found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public FilterRegistrationBean<AccessLoggingFilter> loggingFilter(AuditRepository repository) {
        FilterRegistrationBean<AccessLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AccessLoggingFilter(repository));
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(4);// Apply to specific URL patterns, or use "/*" for all URLs
        return registrationBean;
    }


}

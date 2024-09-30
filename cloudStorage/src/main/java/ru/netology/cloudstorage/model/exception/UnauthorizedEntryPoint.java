package ru.netology.cloudstorage.model.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    private final HttpStatus httpStatus;

    public UnauthorizedEntryPoint(HttpStatus httpStatus) {
        Assert.notNull(httpStatus, "httpStatus cannot be null");
        this.httpStatus = httpStatus;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        response.setStatus(this.httpStatus.value());
        response.setHeader("Description", "Authorization error");
    }
}
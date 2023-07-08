package com.datn.dongho5s.Security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final String clientLoginPage;
    private final String adminLoginPage;

    public CustomAuthenticationEntryPoint(String clientLoginPage, String adminLoginPage) {
        this.clientLoginPage = clientLoginPage;
        this.adminLoginPage = adminLoginPage;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String redirectUrl = "";
        if (request.isUserInRole("CLIENT")) {
            redirectUrl = clientLoginPage;
        } else if (request.isUserInRole("ADMIN_ROOT") || request.isUserInRole("ADMIN_STAFF")) {
            redirectUrl = adminLoginPage;
        }
        response.sendRedirect(redirectUrl);
    }
}

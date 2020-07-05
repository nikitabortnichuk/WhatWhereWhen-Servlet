package com.bortni.controller.filter;

import com.bortni.controller.security.Role;
import com.bortni.controller.security.SecurityUtils;
import com.bortni.controller.util.Routes;
import com.bortni.controller.util.UrlPath;
import com.bortni.model.entity.Admin;
import com.bortni.model.entity.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getPathInfo();
        User signedInUser = (User) request.getSession().getAttribute("userSession");
        Admin admin = (Admin) request.getSession().getAttribute("adminSession");

        if (UrlPath.SIGN_IN.equals(path) && signedInUser != null) {
            response.sendRedirect("/game-www" + UrlPath.USER_PROFILE);
            return;
        }

        if (UrlPath.ADMIN.equals(path) && admin != null) {
            response.sendRedirect("/game-www" + UrlPath.ADMIN_SHOW_QUESTIONS);
            return;
        }

        if (SecurityUtils.isSecurityPageByRole(request, Role.USER)) {
            checkUserSignedIn(filterChain, request, response, signedInUser);
        } else if (SecurityUtils.isSecurityPageByRole(request, Role.ADMIN)) {
            checkAdminSignedIn(filterChain, request, response, admin);
        } else {
            filterChain.doFilter(request, response);
        }

    }

    private void checkUserSignedIn(FilterChain filterChain, HttpServletRequest request, HttpServletResponse response, User signedInUser) throws IOException, ServletException {
        if (signedInUser != null && SecurityUtils.hasPermission(request, Role.USER)) {
            filterChain.doFilter(request, response);
        } else {
            request.getRequestDispatcher(Routes.SIGN_IN).forward(request, response);
        }
    }

    private void checkAdminSignedIn(FilterChain filterChain, HttpServletRequest request, HttpServletResponse response, Admin admin) throws IOException, ServletException {
        if (admin != null && SecurityUtils.hasPermission(request, Role.ADMIN)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(401);
        }
    }

    @Override
    public void destroy() {

    }
}

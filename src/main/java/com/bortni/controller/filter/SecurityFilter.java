package com.bortni.controller.filter;

import com.bortni.controller.command.Error403Command;
import com.bortni.controller.security.Role;
import com.bortni.controller.security.SecurityUtils;
import com.bortni.controller.util.UrlPath;
import com.bortni.model.entity.Admin;
import com.bortni.model.entity.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class SecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getPathInfo();
        User signedInUser = (User) request.getSession().getAttribute("userSession");
        Admin admin = (Admin) request.getSession().getAttribute("adminSession");

        if(UrlPath.SIGN_IN.equals(path) && signedInUser != null){
            response.sendRedirect("/library" + UrlPath.USER_PROFILE);
            return;
        }
        if (UrlPath.ADMIN.equals(path) && admin != null){
            response.sendRedirect("/library" + UrlPath.ADMIN_SHOW_QUESTIONS);
            return;
        }

        if(SecurityUtils.isSecurityPage(request)){
            if((signedInUser != null && SecurityUtils.hasPermission(request, Role.USER)) ||
                    (admin != null && SecurityUtils.hasPermission(request, Role.ADMIN))){
                filterChain.doFilter(request, response);
            }
            else {
                new Error403Command().execute(request, response);
            }
        }
        else {
            filterChain.doFilter(request, response);
        }

    }
}

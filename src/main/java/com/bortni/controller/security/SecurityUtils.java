package com.bortni.controller.security;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public class SecurityUtils {

    public static boolean isSecurityPageByRole(HttpServletRequest request, Role role) {

        List<String> urlPatterns = SecurityConfig.getAllUrlPatternsForRole(role);
        return urlPatterns != null && urlPatterns.contains(request.getPathInfo());
    }

    public static boolean hasPermission(HttpServletRequest request, Role role) {
        return SecurityConfig.getAllUrlPatternsForRole(role).contains(request.getPathInfo());
    }
}

package com.bortni.controller.security;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public class SecurityUtils {
    public static boolean isSecurityPage(HttpServletRequest request){
        Set<Role> roles = SecurityConfig.getAllRoles();

        for (Role role: roles){
            List<String> urlPatterns = SecurityConfig.getAllUrlPatternsForRole(role);
            if(urlPatterns != null && urlPatterns.contains(request.getPathInfo())){
                return true;
            }
        }
        return false;
    }

    public static boolean hasPermission(HttpServletRequest request, Role role){
        return SecurityConfig.getAllUrlPatternsForRole(role).contains(request.getPathInfo());
    }
}

package com.bortni.controller.security;

import com.bortni.controller.util.UrlPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SecurityConfig {
    private static final Map<Role, List<String>> mapConfig = new HashMap<>();

    static {
        init();
    }

    private static void init() {

        List<String> userUrlPatterns = new ArrayList<>();
        userUrlPatterns.add(UrlPath.USER_PROFILE);
        userUrlPatterns.add(UrlPath.CREATE_GAME);
        userUrlPatterns.add(UrlPath.FIND_GAME);
        userUrlPatterns.add(UrlPath.GAME);
        mapConfig.put(Role.USER, userUrlPatterns);

        List<String> adminUrlPatterns = new ArrayList<>();
        adminUrlPatterns.add(UrlPath.ADMIN_SHOW_QUESTIONS);
        adminUrlPatterns.add(UrlPath.ADMIN_ADD_QUESTION);
        adminUrlPatterns.add(UrlPath.ADMIN_UPDATE_PAGE_QUESTION);
        adminUrlPatterns.add(UrlPath.ADMIN_UPDATE_QUESTION);
        adminUrlPatterns.add(UrlPath.ADMIN_DELETE_QUESTION);
        mapConfig.put(Role.ADMIN, adminUrlPatterns);
    }

    public static Set<Role> getAllRoles(){
        return mapConfig.keySet();
    }

    public static List<String> getAllUrlPatternsForRole(Role role){
        return mapConfig.get(role);
    }
}

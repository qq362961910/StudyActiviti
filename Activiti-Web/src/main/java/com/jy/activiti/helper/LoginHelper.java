package com.jy.activiti.helper;

import activiti.entity.User;
import com.jy.activiti.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class LoginHelper {

    private static final String SESSION_KEY = "login_session";

    @Autowired
    private CookieHelper cookieHelper;

    private final Map<String, User> sessionHolder = new HashMap<>();

    /**
     * 创建session
     * */
    public String createSession(User user, HttpServletResponse response) {
        String key = UUID.randomUUID().toString();
        sessionHolder.put(key, user);
        if (response != null) {
            cookieHelper.addCookie(response, SESSION_KEY, key);
        }
        return key;
    }

    public User getSessionCacheUser(String key) {
        return sessionHolder.get(key);
    }

    public User getSessionCacheUser(HttpServletRequest request) {
        String sessionKey = cookieHelper.getCookieValue(request, SESSION_KEY);
        return sessionHolder.get(sessionKey);
    }

    public User login(String username, String password, String code) {
        if (username.equals("admin") && password.equals("admin")) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("admin");
            return user;
        }
        return null;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String sessionKey = cookieHelper.getCookieValue(request, SESSION_KEY);
        //清楚缓存用户
        if (!StringUtil.isEmpty(sessionKey)) {
            sessionHolder.remove(sessionKey);
        }
        //清除cookie
        Cookie cookie = new Cookie(SESSION_KEY, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}

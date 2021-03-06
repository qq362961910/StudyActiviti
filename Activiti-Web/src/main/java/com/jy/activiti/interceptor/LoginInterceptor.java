package com.jy.activiti.interceptor;

import com.jy.activiti.common.annotation.RequiredLogin;
import com.jy.activiti.helper.ContextHelper;
import com.jy.activiti.helper.LoginHelper;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private LoginHelper loginHelper;
    @Autowired
    private ContextHelper contextHelper;
    @Autowired
    private IdentityService identityService;

    public LoginInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Annotation controllerRequireLogin = handlerMethod.getBeanType().getAnnotation(RequiredLogin.class);
            boolean needLogincheck = true;
            if (controllerRequireLogin == null) {
                Annotation methodRequireLogin = handlerMethod.getMethodAnnotation(RequiredLogin.class);
                if (methodRequireLogin == null) {
                    needLogincheck = false;
                }
            }
            if (needLogincheck) {
                User user = loginHelper.getSessionCacheUser(request);
                if (user == null) {
                    response.sendRedirect("/");
                    return false;
                }
                contextHelper.setCurrentUser(user);
                identityService.setAuthenticatedUserId(user.getId());
                return true;
            }
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        contextHelper.removeCurrentUser();
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}

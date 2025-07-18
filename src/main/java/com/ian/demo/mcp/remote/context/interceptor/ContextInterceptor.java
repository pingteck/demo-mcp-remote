package com.ian.demo.mcp.remote.context.interceptor;

import com.ian.demo.mcp.remote.context.model.Context;
import com.ian.demo.mcp.remote.context.model.ContextParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Log4j2
@Component
public class ContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        Context.init();
        Context.set(ContextParam.REQUEST_ID, UUID.randomUUID());
        Context.set(ContextParam.REQUEST_URL, request.getRequestURL().toString());
        Context.set(ContextParam.REQUEST_METHOD, request.getMethod());

        Map<String, String> headers = Collections.list(request.getHeaderNames())
            .stream()
            .collect(Collectors.toMap(h -> h, request::getHeader));
        log.info("Headers: {}", headers);

        String sessionId = request.getParameter("sessionId");
        if (sessionId != null) {
            Context.set(ContextParam.SESSION_ID, sessionId);
        }

        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            Context.set(ContextParam.AUTHORIZATION, authorization);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Context.set(ContextParam.AUTHENTICATION, authentication);
        if (authentication != null) {
            String name = authentication.getName();
            Object credentials = authentication.getCredentials();
            Object details = authentication.getDetails();
            Object principal = authentication.getPrincipal();
            List<? extends GrantedAuthority> authorities = List.copyOf(
                authentication.getAuthorities());
            Context.set(ContextParam.AUTH_USER_POOL_ID, name);
            Context.set(ContextParam.AUTH_CREDENTIALS, credentials);
            Context.set(ContextParam.AUTH_DETAILS, details);
            Context.set(ContextParam.AUTH_PRINCIPAL, principal);
            Context.set(ContextParam.AUTH_AUTHORITIES, authorities);
        }
        log.debug("[preHandle] {}", Context.getContextString(Context.getContext()));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {
        log.info("[afterCompletion] contextId: {}, requestId: {}",
            Context.get(ContextParam.CONTEXT_ID), Context.get(ContextParam.REQUEST_ID));
        Context.clear();
        log.debug("[afterCompletion] {}", Context.getContextString(Context.getContext()));
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}

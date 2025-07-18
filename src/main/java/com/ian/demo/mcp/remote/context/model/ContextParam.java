package com.ian.demo.mcp.remote.context.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContextParam {

    CONTEXT_ID("context-id"),

    REQUEST_ID("request-id"),
    REQUEST_URL("request-url"),
    REQUEST_METHOD("request-method"),

    AUTHORIZATION("authorization"),

    SESSION_ID("session-id"),

    AUTHENTICATION("authentication"),
    AUTH_USER_POOL_ID("user-pool-id"),
    AUTH_CREDENTIALS("credentials"),
    AUTH_DETAILS("details"),
    AUTH_PRINCIPAL("principal"),
    AUTH_AUTHORITIES("authorities"),
    ;

    private final String key;

}

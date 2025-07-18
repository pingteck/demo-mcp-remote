package com.ian.demo.mcp.remote.context.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.core.NamedThreadLocal;

public class Context {

    private static final ThreadLocal<Map<ContextParam, Object>> contextThread = new NamedThreadLocal<>(
        "custom-context-thread");
//    private static final InheritableThreadLocal<Map<ContextParam, Object>> contextThread = new NamedInheritableThreadLocal<>(
//        "custom-context-thread");

    private Context() {
    }

    public static void init() {
        if (contextThread.get() != null) {
            throw new IllegalStateException("Context has already been initialized");
        }
        Map<ContextParam, Object> contextMap = new HashMap<>();
        contextMap.put(ContextParam.CONTEXT_ID, UUID.randomUUID());
        contextThread.set(contextMap);
    }

    public static Map<ContextParam, Object> getContext() {
        return contextThread.get();
    }

    public static void setContext(Map<ContextParam, Object> context) {
        contextThread.set(context);
    }

    public static Object get(ContextParam param) {
        return contextThread.get().get(param);
    }

    public static void set(ContextParam param, Object value) {
        contextThread.get().put(param, value);
    }

    public static void clear() {
        contextThread.remove();
    }

    public static String getContextString(Map<ContextParam, Object> contextMap) {
        StringBuilder sb = new StringBuilder("\nCurrent Context\n");
        if (contextMap != null) {
            for (Map.Entry<ContextParam, Object> entry : contextMap.entrySet()) {
                ContextParam param = entry.getKey();
                Object value = entry.getValue();
                sb.append(param).append("=").append(value).append("\n");
            }
        }
        return sb.toString();
    }

}

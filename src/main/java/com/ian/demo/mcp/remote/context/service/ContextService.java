package com.ian.demo.mcp.remote.context.service;

import com.ian.demo.mcp.remote.context.model.Context;
import com.ian.demo.mcp.remote.context.model.ContextParam;
import com.ian.demo.mcp.remote.context.model.ContextSnapshot;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ContextService {

    public ContextSnapshot getContextSnapshot() {
        if (Context.getContext() != null) {
            return new ContextSnapshot(new HashMap<>(Context.getContext()));
        }
        return new ContextSnapshot(new HashMap<>());
    }

    public void restoreContextSnapshot(ContextSnapshot snapshot) {
        Map<ContextParam, Object> context = snapshot.getContext();
        if (context != null && context.get(ContextParam.AUTHENTICATION) != null) {
            if (context.get(ContextParam.AUTHENTICATION) instanceof Authentication authentication) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        Context.setContext(snapshot.getContext());
    }

    public void clearContext() {
        SecurityContextHolder.clearContext();
        Context.clear();
    }

}

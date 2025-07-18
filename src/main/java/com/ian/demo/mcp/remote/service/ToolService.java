package com.ian.demo.mcp.remote.service;

import com.ian.demo.mcp.remote.context.model.Context;
import com.ian.demo.mcp.remote.context.service.ContextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ToolService {

    private final ContextService contextService;

    @PreAuthorize("hasAuthority('SCOPE_demo-mcp-remote-dev/tool.sayHello')")
    @Tool(name = "sayHello",
        description = """
            Say hello to user
            """)
    public String sayHello(
        @ToolParam(description = "The name of the user to say hello to") String name) {
        log.info("[sayHello] {}",
            Context.getContextString(contextService.getContextSnapshot().getContext()));
        return "Hello " + name;
    }

    @PreAuthorize("hasAuthority('SCOPE_demo-mcp-remote-dev/tool.sayBye')")
    @Tool(name = "sayBye",
        description = """
            Say bye to user
            """)
    public String sayBye(
        @ToolParam(description = "The name of the user to say bye to") String name) {
        log.info("[sayBye] {}",
            Context.getContextString(contextService.getContextSnapshot().getContext()));
        return "Bye " + name;
    }

}

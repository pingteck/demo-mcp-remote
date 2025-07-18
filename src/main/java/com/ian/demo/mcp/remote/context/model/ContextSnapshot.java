package com.ian.demo.mcp.remote.context.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ContextSnapshot {

    private Map<ContextParam, Object> context;

}

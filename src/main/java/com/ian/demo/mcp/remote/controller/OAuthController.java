package com.ian.demo.mcp.remote.controller;

import com.ian.demo.mcp.remote.config.AppConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final AppConfig appConfig;

    @GetMapping("/.well-known/oauth-protected-resource${app.base-path}")
    public ResponseEntity<Map<String, Object>> oauthProtectedResources() {
        Map<String, Object> map = new HashMap<>();
        map.put("resource", appConfig.getHost() + appConfig.getBasePath() + "/sse");
        List<String> authorizationServers = new ArrayList<>();
        authorizationServers.add(appConfig.getIssuerUri());
        map.put("authorization_servers", authorizationServers);
        map.put("jwks_uri", appConfig.getJwksUri());
        List<String> bearerMethods = new ArrayList<>();
        bearerMethods.add("header");
        map.put("bearer_methods_supported", bearerMethods);
        map.put("resource_name", "Demo Remote MCP Server");
        return ResponseEntity.ok(map);
    }

}

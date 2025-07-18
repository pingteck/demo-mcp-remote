# Demo MCP Remote

A Spring Boot application demonstrating the implementation of a remote Model Context Protocol (MCP) server using Spring AI.

## Overview

This project showcases a remote MCP server implementation with OAuth2 authentication using AWS Cognito. It provides a secure way to expose AI tools through a standardized protocol.

## Features

- Remote MCP server implementation using Spring AI
- OAuth2 authentication with AWS Cognito
- Custom context management for request tracking
- Tool implementation with method-based approach
- CORS configuration for frontend integration

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- AWS Cognito user pool

## Configuration

The application requires the following environment variables:

```
AWS_COGNITO_REGION=<your-cognito-region>
AWS_COGNITO_USER_POOL=<your-cognito-user-pool-id>
```

## Tools

The application exposes the following AI tools:

- `sayHello`: Greets a user by name
- `sayBye`: Says goodbye to a user by name

Each tool requires specific OAuth2 scopes for authorization:
- `demo-mcp-remote-dev/tool.sayHello`
- `demo-mcp-remote-dev/tool.sayBye`

## Building and Running

### Build the application

```bash
mvn clean package
```

### Run the application

```bash
java -jar target/app.jar
```

The application will start on port 8080 with the base path `/test`.

## API Endpoints

- MCP SSE Endpoint: `/test/sse`
- MCP Message Endpoint: `/test/mcp/message`
- OAuth Protected Resource: `/.well-known/oauth-protected-resource/test`

## Security

The application uses Spring Security with OAuth2 resource server configuration. All endpoints except for actuators and `.well-known` endpoints require authentication.

## Development

This project uses:
- Spring Boot 3.5.3
- Spring AI MCP Server
- Log4j2 for logging
- Lombok for reducing boilerplate code

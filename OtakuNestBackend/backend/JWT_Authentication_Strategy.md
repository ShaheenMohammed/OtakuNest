# JWT Authentication Strategy

This document outlines the authentication and authorization strategy for the OtakuNest media server. OtakuNest utilizes JSON Web Tokens (JWT) to secure communication between the React frontend and the Spring Boot backend.

## Overview

The system employs a dual-token architecture consisting of an **Access Token** and a **Refresh Token**. This approach balances security and user experience by minimizing the lifespan of the highly privileged Access Token while using a longer-lived Refresh Token to maintain user sessions seamlessly.

## User Roles and Authorization Boundaries

OtakuNest employs Role-Based Access Control (RBAC) with two strict user roles. The user's role is embedded as a claim within the generated JWT Access Token, allowing the backend to instantly authorize or deny requests.

### 1. The `ADMIN` Role
*   **Scope**: Full, unrestricted access to the complete application.
*   **Limitation**: There is only **one** admin user in the system. The admin manages the real media library, configures settings, and has read/write privileges over all endpoints.

### 2. The `DEMO` Role
*   **Scope**: Limited, read-only access restricted strictly to the demo environment. 
*   **Limitation**: There can be **many** demo users. Demo users cannot access the real media library, alter settings, edit metadata, or trigger library scans. Their API access is boundary-limited to endpoints serving placeholder/sample data.

## Token Types and Storage Boundaries

### 1. Access Token
*   **Purpose**: Used to authorize API requests to protected resources (e.g., fetching media, updating watch progress).
*   **Lifespan**: Short-lived (e.g., 15 minutes).
*   **Storage (Frontend)**: Stored strictly in **application memory** (e.g., React Context or Redux).
*   **Security Rationale**: By keeping the Access Token in memory rather than `localStorage`, it is protected against Cross-Site Scripting (XSS) attacks. If an attacker injects malicious JavaScript, they cannot easily scrape the token from storage.

### 2. Refresh Token
*   **Purpose**: Used exclusively to obtain a new Access Token when the current one expires.
*   **Lifespan**: Long-lived (e.g., 7 days).
*   **Storage (Frontend)**: Stored as an **`HttpOnly` Cookie**.
*   **Security Rationale**: The `HttpOnly` flag ensures that the cookie cannot be accessed via JavaScript (`document.cookie`), neutralizing XSS threats against the Refresh Token. The cookie is also marked as `Secure` (transmitted only over HTTPS) and `SameSite=Strict` (mitigating Cross-Site Request Forgery - CSRF).

## Authentication Flow

### 1. Login (Token Generation)
1.  The user submits their username and password via the React frontend.
2.  The Spring Boot backend verifies the credentials against the `users` table.
3.  Upon success, the backend generates both tokens.
4.  The backend returns the Access Token in the JSON response body and sets the Refresh Token via a `Set-Cookie` HTTP header.

### 2. Accessing Protected Resources
1.  The React app attaches the Access Token to the `Authorization: Bearer <token>` header of every outgoing API request using an Axios interceptor.
2.  The Spring Boot backend intercepts the request using a security filter, verifies the token's signature and expiration, and extracts the user's UUID.
3.  If valid, the request proceeds to the controller.

### 3. Silent Token Refresh
1.  When the Access Token expires, an API request will fail with a `401 Unauthorized` status.
2.  The Axios interceptor catches this error and automatically sends a request to the `/api/auth/refresh` endpoint.
3.  The browser automatically includes the `HttpOnly` Refresh Token cookie with this request.
4.  The backend validates the Refresh Token and responds with a new Access Token.
5.  The frontend updates its in-memory Access Token and automatically retries the originally failed request, making the refresh process invisible to the user.

### 4. Logout (Token Revocation)
1.  The user clicks "Logout".
2.  The React app discards the Access Token from memory.
3.  The frontend calls the `/api/auth/logout` endpoint.
4.  The backend invalidates the session by returning a `Set-Cookie` header that expires and deletes the Refresh Token cookie.

## Security Considerations

*   **XSS Mitigation**: Neither token is accessible to JavaScript via persistent storage. The Access Token is memory-only (wiped on page refresh), and the Refresh Token is `HttpOnly`.
*   **CSRF Mitigation**: Since the Access Token is sent via the `Authorization` header, it is immune to CSRF. The Refresh Token is sent via cookie, but is protected by the `SameSite=Strict` attribute and is only valid for the `/refresh` endpoint.
*   **Statelessness**: The backend does not need to store active sessions in memory, allowing the API to remain stateless and easily scalable.

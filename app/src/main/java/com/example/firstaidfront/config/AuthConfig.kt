package com.example.firstaidfront.config

object AuthConfig {
    const val AUTH_URI = "http://10.0.2.2:8080/realms/first-aid/protocol/openid-connect/auth"
    const val TOKEN_URI = "http://10.0.2.2:8080/realms/first-aid/protocol/openid-connect/token"
    const val END_SESSION_URI = "http://10.0.2.2:8080/realms/first-aid/protocol/openid-connect/logout"
    // Changed to match Keycloak setting - single slash
    const val REDIRECT_URI = "com.firstaid.app:/oauth2callback"
    const val CLIENT_ID = "mobile-first-aid"
    const val SCOPE = "openid"
}
package com.example.firstaidfront.repository


import com.example.firstaidfront.api.AuthService
import com.example.firstaidfront.config.ApiClient
import com.example.firstaidfront.models.AuthResponse


class AuthRepository {
    private val authService = ApiClient.create(AuthService::class.java)

    suspend fun exchangeToken(code: String): AuthResponse {
        return authService.login(code)
    }
}

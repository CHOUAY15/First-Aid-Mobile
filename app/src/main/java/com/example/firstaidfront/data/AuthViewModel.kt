package com.example.firstaidfront.data


import androidx.lifecycle.ViewModel
import com.example.firstaidfront.models.AuthResponse
import com.example.firstaidfront.repository.AuthRepository

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    suspend fun handleAuthCode(code: String): AuthResponse {
        return repository.exchangeToken(code)
    }
}
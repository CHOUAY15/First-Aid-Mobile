package com.example.firstaidfront.data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.firstaidfront.models.AuthResponse
import com.example.firstaidfront.repository.AuthRepository

class AuthViewModel : ViewModel() {
    private val TAG = "AuthViewModel"
    private val authRepository = AuthRepository()

    suspend fun handleAuthCode(code: String): AuthResponse {
        Log.d(TAG, "Handling auth code")
        try {
            val response = authRepository.login(code)
            Log.d(TAG, "Auth code handled successfully")
            return response
        } catch (e: Exception) {
            Log.e(TAG, "Failed to handle auth code: ${e.message}", e)
            throw e
        }
    }
}
package com.example.firstaidfront.repository

import android.util.Log
import com.example.firstaidfront.api.AuthService
import com.example.firstaidfront.config.ApiClient
import com.example.firstaidfront.models.AuthResponse
import okhttp3.ResponseBody
import retrofit2.HttpException

class AuthRepository {
    private val TAG = "AuthRepository"

    private val authService = ApiClient.create(AuthService::class.java)

    suspend fun login(code: String): AuthResponse {
        Log.d(TAG, "Attempting login with code: ${code.take(10)}...")
        try {
            val response = authService.login(code)
            Log.d(TAG, "Login successful - User ID: ${response.userId}")
            return response
        } catch (e: HttpException) {
            // Extract the error response body and log it
            val errorBody: ResponseBody? = e.response()?.errorBody()
            val errorContent = errorBody?.string() // Convert error body to string
            Log.e(TAG, "HTTP Exception - Code: ${e.code()}, Error Body: $errorContent", e)
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Login failed: ${e.message}", e)
            throw e
        }
    }
}

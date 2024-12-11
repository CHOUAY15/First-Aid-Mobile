package com.example.firstaidfront.repository

import android.content.Context
import com.example.firstaidfront.api.TrainingService
import com.example.firstaidfront.config.ApiClient
import com.example.firstaidfront.models.Category

class TrainingRepository(context: Context) {
    private val trainingService = ApiClient.create(TrainingService::class.java,context)

    suspend fun getTrainings(): List<Category> = trainingService.getTrainings()
}
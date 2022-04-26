package com.example.brochureapp.domain.repository

import com.example.brochureapp.common.Resource
import com.example.brochureapp.domain.entities.Content
import kotlinx.coroutines.flow.Flow

interface BrochureRepository {
    suspend fun getAllBrochures(): Flow<Resource<List<Content>>>
}
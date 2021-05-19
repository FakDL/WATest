package com.example.domain.interfaces

import com.example.domain.models.Landmark

interface LandmarkRepository {
    suspend fun getLandmarks(): List<Landmark>
}
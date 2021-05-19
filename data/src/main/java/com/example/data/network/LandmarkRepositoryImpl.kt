package com.example.data.network

import com.example.domain.interfaces.LandmarkRepository
import com.example.domain.models.Landmark

class LandmarkRepositoryImpl (
    private val landmarkService: LandmarkService
) : LandmarkRepository {
    override suspend fun getLandmarks(): List<Landmark> {
        return landmarkService.getLandmarks()
    }
}
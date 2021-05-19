package com.example.data.di

import com.example.data.db.LandDBRepositoryImpl
import com.example.data.network.LandmarkRepositoryImpl
import com.example.data.network.LandmarkService
import com.example.domain.interfaces.LandDBRepository
import com.example.domain.interfaces.LandmarkRepository
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepoModule {

    @Singleton
    @Provides
    fun provideLandmarkRepository(
        landmarkService: LandmarkService
    ): LandmarkRepository {
        return LandmarkRepositoryImpl(landmarkService)
    }

    @Singleton
    @Provides
    fun provideLandDBRepository(
        firebaseDatabase: FirebaseDatabase
    ): LandDBRepository {
        return LandDBRepositoryImpl(firebaseDatabase)
    }

}
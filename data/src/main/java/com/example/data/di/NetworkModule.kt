package com.example.data.di

import com.example.data.BuildConfig
import com.example.data.network.LandmarkService
import com.example.data.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideFilmService(retrofitBuilder: Retrofit.Builder): LandmarkService {
        return retrofitBuilder
            .build()
            .create(LandmarkService::class.java)
    }

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val originalUrl = original.url
                val url = originalUrl.newBuilder()
                    .addQueryParameter("alt", "media")
                    .addQueryParameter("token", BuildConfig.TOKEN)
                    .build()
                val request = original.newBuilder()
                    .url(url).build()
                chain.proceed(request)
            }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
    }
}
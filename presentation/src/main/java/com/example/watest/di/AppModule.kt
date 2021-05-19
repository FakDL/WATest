package com.example.watest.di

import android.content.SharedPreferences
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    @Singleton
    @Provides
    fun provideFragmentFactory(
        viewModelFactory: ViewModelProvider.Factory,
    ): FragmentFactory {
        return MainFragmentFactory(
            viewModelFactory
        )
    }
}

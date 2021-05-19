package com.example.watest.di

import android.app.Application
import com.example.data.di.DBModule
import com.example.data.di.NetworkModule
import com.example.data.di.RepoModule
import com.example.watest.di.viewmodel.ViewModelModule
import com.example.watest.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        RepoModule::class,
        NetworkModule::class,
        DBModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
}

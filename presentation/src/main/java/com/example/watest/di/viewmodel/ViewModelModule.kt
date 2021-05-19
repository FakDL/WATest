package com.example.watest.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.watest.viewmodels.DetailsViewModel
import com.example.watest.viewmodels.ListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {
    @Singleton
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun bindFilmsViewModel(viewModel: ListViewModel): ViewModel

}

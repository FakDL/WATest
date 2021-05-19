package com.example.watest.di

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.example.watest.ui.fragments.DetailsFragment
import com.example.watest.ui.fragments.ListFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainFragmentFactory
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when (className) {

            DetailsFragment::class.java.name -> {
                DetailsFragment(viewModelFactory)
            }


            ListFragment::class.java.name -> {
                ListFragment(viewModelFactory)
            }

            else -> {
                ListFragment(viewModelFactory)
            }
        }
}

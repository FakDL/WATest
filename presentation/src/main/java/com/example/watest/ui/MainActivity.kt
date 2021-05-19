package com.example.watest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import com.example.watest.BaseApplication
import com.example.watest.MainNavHostFragment
import com.example.watest.R
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var navHost: NavHostFragment

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        setContentView(R.layout.activity_main)
        createNavHost()
    }


    fun inject() {
        (application as BaseApplication).appComponent.inject(this)
    }

    private fun createNavHost(){
        navHost = MainNavHostFragment.create(
            R.navigation.main_nav_graph
        )
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragments_container,
                navHost,
                getString(R.string.main_nav_host)
            )
            .setPrimaryNavigationFragment(navHost)
            .commit()
    }
}

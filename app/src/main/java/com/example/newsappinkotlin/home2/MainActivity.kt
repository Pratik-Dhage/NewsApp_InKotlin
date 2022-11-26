package com.example.newsappinkotlin.home2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.newsappinkotlin.R
import com.example.newsappinkotlin.account.AccountFragment
import com.example.newsappinkotlin.databinding.ActivityMainBinding
import com.example.newsappinkotlin.home.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var view: View
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeFields()
        setUpBottomNavigation()
    }

    private fun initializeFields() {
      binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        view = binding.root
    }

    private fun setUpBottomNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment?
        val bottomNav: BottomNavigationView = binding.bottomNav

        if (navHostFragment != null) {
            navController = navHostFragment.navController
        }





        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.nav_home,
                R.id.nav_account,
                ), fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )


        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {

            R.id.nav_home->{
                HomeFragment()
                          }
                R.id.nav_account->{
                AccountFragment()
                }

            }
        }
    }
}
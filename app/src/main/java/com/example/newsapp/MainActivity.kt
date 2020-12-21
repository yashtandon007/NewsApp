package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.paging.PagingData
import com.example.newsapp.adapter.NewsListAdapter
import com.example.newsapp.api.NewsService
import com.example.newsapp.data.NewsRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.navigation.plusAssign

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var appBarConfiguration:AppBarConfiguration
    lateinit var navController:NavController;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = my_nav_host_fragment.findNavController()
        appBarConfiguration = AppBarConfiguration(setOf(R.id.viewPagerFragment,R.id.offlineFragment,R.id.settingsFragment),drawer_layout)
        toolbar.setupWithNavController(navController,appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.viewPagerFragment -> showBottomNav()
                R.id.settingsFragment -> showBottomNav()
                R.id.offlineFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }
        NavigationUI.setupWithNavController(bottom_nav,
            navController);
    }

    private fun showBottomNav() {
        bottom_nav.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        bottom_nav.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}
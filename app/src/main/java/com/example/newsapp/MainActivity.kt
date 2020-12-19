package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
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

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var appBarConfiguration:AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.viewPagerFragment,R.id.offlineFragment,R.id.settingsFragment),drawer_layout)
        toolbar.setupWithNavController(nav_host.findNavController(),appBarConfiguration)
        nav_host.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.viewPagerFragment || destination.id == R.id.settingsFragment || destination.id ==R.id.offlineFragment) {
                bottom_nav.visibility = View.VISIBLE
            } else {
                bottom_nav.visibility = View.GONE
            }
        }
        NavigationUI.setupWithNavController(bottom_nav,
            nav_host.findNavController());
    }

    override fun onSupportNavigateUp(): Boolean {
        return nav_host.findNavController().navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}
package com.example.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.newsapp.adapter.NewsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {


    private val viewModel: HomeViewModel by viewModels()
    private var adapterNews = NewsListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        rv_news.layoutManager = LinearLayoutManager(context)
        rv_news.adapter = adapterNews
        loadData()
        animateCenterItem()
        viewLifecycleOwner.lifecycleScope.launch {
            adapterNews.loadStateFlow.collectLatest { loadStates ->
                pbar_center.isVisible = loadStates.refresh is LoadState.Loading
                pbar_bottom.isVisible = loadStates.append is LoadState.Loading
            }
        }
        swiperefresh.setOnRefreshListener {
            loadData()
        }
    }

    private fun animateCenterItem() {
        val connectingAnimation: Animation =
            AnimationUtils.loadAnimation(context, R.anim.scale_image)
        val snapHelper: SnapHelper = object : LinearSnapHelper() {
            var iv_Previous: ImageView? = null
            override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
                var root = super.findSnapView(layoutManager)
                root?.let {
                    var cardView = root as CardView
                    var frameLayout = cardView.getChildAt(0) as FrameLayout
                    var imageview = frameLayout.getChildAt(0) as ImageView
                    iv_Previous?.let {
                        it.clearAnimation()
                        connectingAnimation.cancel()
                    }
                    imageview.startAnimation(connectingAnimation);
                    iv_Previous = imageview
                }
                return null
            }
        }
        snapHelper.attachToRecyclerView(rv_news);
    }

    private fun loadData() {
        arguments?.getString("query")?.let {
            viewLifecycleOwner. lifecycleScope.launch {
               viewModel.getData(it).collect {
                   adapterNews.submitData(it)
               }
           }
        }
        swiperefresh.isRefreshing = false
    }

}
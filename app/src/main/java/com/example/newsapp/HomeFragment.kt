package com.example.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {


    private lateinit var viewModel: HomeViewModel
    private var adapterNews = NewsListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        var layoutManager = LinearLayoutManager(context)
        rv_news.layoutManager = layoutManager
        rv_news.adapter = adapterNews
        viewModel.getNewsList().observe(viewLifecycleOwner, {
            GlobalScope.launch {
                adapterNews.submitData(it)
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            adapterNews.loadStateFlow.collectLatest { loadStates ->
                pbar_center.isVisible = loadStates.refresh is LoadState.Loading
                pbar_bottom.isVisible = loadStates.append is LoadState.Loading
            }
        }
        val snapHelper: SnapHelper = object :LinearSnapHelper(){
            override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
                return super.findSnapView(layoutManager)

            }

            override fun findTargetSnapPosition(
                layoutManager: RecyclerView.LayoutManager?,
                velocityX: Int,
                velocityY: Int
            ): Int {
                return super.findTargetSnapPosition(layoutManager, velocityX, velocityY)

            }
        }
        snapHelper.attachToRecyclerView(rv_news);
    }

}
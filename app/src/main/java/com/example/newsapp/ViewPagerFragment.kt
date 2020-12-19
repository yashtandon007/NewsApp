package com.example.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_viewpager.*
import kotlinx.android.synthetic.main.home_fragment.*


@AndroidEntryPoint
class ViewPagerFragment : Fragment() {

    private lateinit var demoCollectionPagerAdapter: DemoCollectionPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_viewpager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        demoCollectionPagerAdapter = DemoCollectionPagerAdapter(childFragmentManager)
        // val mypager:ViewPager = view.findViewById(R.id.mypager)
        mypager.adapter = demoCollectionPagerAdapter
        tab_layout.setupWithViewPager(mypager)
    }

    class DemoCollectionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int = 8

        override fun getItem(i: Int): Fragment {
            var str = getPageTitle(i).toString().toLowerCase()
            var homeFragment = HomeFragment()
            var bundle = Bundle()
            bundle.putString("query", str)
            homeFragment.arguments = bundle
            return homeFragment
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> "HOME"
                1 -> "WORLD"
                2 -> "SCIENCE"
                3 -> "SPORT"
                4 -> "ENVIRONMENT"
                5 -> "SOCIETY"
                6 -> "FASHION"
                7 -> "BUSINESS"
                else -> "HOME"
            }
        }
    }
}

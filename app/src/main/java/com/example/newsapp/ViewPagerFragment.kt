package com.example.newsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_viewpager.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.my_progresbar.*
import kotlinx.android.synthetic.main.my_toolbar.*
import kotlinx.android.synthetic.main.my_toolbar.view.*


@AndroidEntryPoint
class ViewPagerFragment : Fragment() {

    private lateinit var demoCollectionPagerAdapter: DemoCollectionPagerAdapter
    private var mainActivity: MainActivity? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity = activity as MainActivity?
        val navController = findNavController()
        toolbar.setupWithNavController(navController, mainActivity?.appBarConfiguration!!)
        mypager.postDelayed(Runnable {
            demoCollectionPagerAdapter = DemoCollectionPagerAdapter(this)
            mypager.adapter = demoCollectionPagerAdapter
            TabLayoutMediator(
                tab_layout, mypager
            ) { tab, position ->
                tab.setText(
                    (mypager.getAdapter() as DemoCollectionPagerAdapter).getPageTitle(
                        position
                    )
                ) //Sets tabs names as mentioned in ViewPagerAdapter fragmentNames array, this can be implemented in many different ways.
            }.attach()
            pbar.visibility  =View.GONE
        }, 111)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_viewpager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(HomeViewModel.TAG, "ViewPagerFragment onViewCreated...")

    }

    class DemoCollectionPagerAdapter(f: Fragment) : FragmentStateAdapter(f) {

        //        override fun getCount(): Int = 8
//
//
        fun getPageTitle(position: Int): CharSequence {
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

        override fun getItemCount(): Int = 8


        override fun createFragment(position: Int): Fragment {
            Log.e("yashtandon", "createFragment: position " + position)
            var str = getPageTitle(position).toString().toLowerCase()
            var homeFragment = HomeFragment()
            var bundle = Bundle()
            bundle.putString("query", str)
            homeFragment.arguments = bundle
            return homeFragment
        }
    }
}

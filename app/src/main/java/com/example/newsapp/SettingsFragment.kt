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
import com.example.newsapp.util.ThemeHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_viewpager.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.settings_fragment.*


@AndroidEntryPoint
class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        my_switch.setOnCheckedChangeListener{ _, isChecked->
                if(isChecked){
                    ThemeHelper.applyTheme(ThemeHelper.DARK_MODE)
                }else{
                    ThemeHelper.applyTheme(ThemeHelper.LIGHT_MODE)
                }
        }
    }


}

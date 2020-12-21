package com.example.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.newsapp.util.ThemeHelper
import dagger.hilt.android.AndroidEntryPoint
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
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val isDarkModeActive = sharedPreferences.getBoolean(ThemeHelper.DARK_MODE, false)
        my_switch.isChecked = isDarkModeActive

        my_switch.setOnCheckedChangeListener{ _, isChecked->
            val editor = sharedPreferences.edit()
                if(isChecked){
                    ThemeHelper.applyTheme(ThemeHelper.DARK_MODE)
                }else{
                    ThemeHelper.applyTheme(ThemeHelper.LIGHT_MODE)
                }
            editor.putBoolean(ThemeHelper.DARK_MODE,isChecked);
            editor.apply();

        }

    }


}

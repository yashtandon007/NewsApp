package com.example.newsapp

import android.R
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.databinding.adapters.SeekBarBindingAdapter.setProgress
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.databinding.FragmentNewsDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_news_detail.*
import kotlinx.android.synthetic.main.my_progresbar.*
import kotlinx.android.synthetic.main.my_toolbar.*

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    private val args: NewsDetailFragmentArgs by navArgs()
    private var mainActivity: MainActivity? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return FragmentNewsDetailBinding.inflate(LayoutInflater.from(context),container,false).root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val navController = findNavController()
        mainActivity = activity as MainActivity?
        toolbar
            .setupWithNavController(navController, mainActivity?.appBarConfiguration!!)

        webview.getSettings().setJavaScriptEnabled(true);
        webview.webChromeClient = MyWebChormeCl(webview,pbar)
        webview.setWebViewClient(MyCustomWebViewClient());
        webview.loadUrl(args.data.webUrl);
    }
    class MyCustomWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return if (Uri.parse(url).getHost()?.endsWith(url) == true) {
                true
            } else false
        }
    }
    class MyWebChormeCl(val webview:WebView,val pbar:ProgressBar) : WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            println("yash tandon progress  "+newProgress)
            if (newProgress >22) {
                webview.visibility = VISIBLE
                pbar.visibility=View.GONE
            }
        }
    }

}

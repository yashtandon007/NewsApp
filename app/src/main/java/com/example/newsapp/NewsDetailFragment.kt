package com.example.newsapp

import android.R
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.adapters.SeekBarBindingAdapter.setProgress
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapp.databinding.FragmentNewsDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news_detail.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    private val args: NewsDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return FragmentNewsDetailBinding.inflate(LayoutInflater.from(context),container,false).root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        my_toolbar.setNavigationOnClickListener(View.OnClickListener {
//            activity!!.onBackPressed() })
        webview.getSettings().setJavaScriptEnabled(true);
        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {

                // Return the app name after finish loading
                if (progress == 100) {
                    pbar.visibility=View.GONE
                }
            }
        }
        webview.setWebViewClient(MyCustomWebViewClient());
        webview.loadUrl(args.data.webUrl);
        webview.webChromeClient = WebChromeClient()
    }
}

//class MyWebChromeClient: WebChromeClient() {
//    override fun onProgressChanged(view: WebView?, newProgress: Int) {
//        super.onProgressChanged(view, newProgress)
//        view.setTitle("Loading, Please wait!");
//        this.setProgress(progress * 100);
//        if (progress == 100) {
//            webview.setTitle(R.string.app_name);
//        }
//    }
//}
internal class MyCustomWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        return if (Uri.parse(url).getHost()?.endsWith(url) == true) {
            true
        } else false
    }
}
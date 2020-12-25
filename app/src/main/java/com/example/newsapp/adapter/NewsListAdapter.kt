package com.example.newsapp.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Html
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.adapters.TextViewBindingAdapter.setTextSize
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import com.example.newsapp.R
import com.example.newsapp.ViewPagerFragmentDirections
import com.example.newsapp.data.dto.NewsItem
import com.example.newsapp.databinding.NewsCardItemBinding

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class NewsListAdapter() : PagingDataAdapter<NewsItem, NewsListAdapter.NewsItemHolder>(NewsDiffUtil()) {

    override fun onBindViewHolder(holder: NewsItemHolder, position: Int) {
            holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemHolder {
        return NewsItemHolder(
            NewsCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    class NewsItemHolder(var holder: NewsCardItemBinding) : RecyclerView.ViewHolder(holder.root) {

        init {

        }
        fun bind(currentNews: NewsItem) {
           // setColorTheme(holder)
           // setTextSize(holder)
            holder.titleCard.setText(currentNews.webTitle)
            holder.sectionCard.setText(currentNews.sectionName)

            var str:String? = formatDate(currentNews.webPublicationDate)
            holder.dateCard.setText(getTimeDifference(str!!))
            holder.trailTextCard.setText(
                Html.fromHtml(
                    Html.fromHtml(currentNews.fields.trailText).toString()
                )
            )
            holder.cardView.setOnClickListener {

                holder.cardView.findNavController().navigate(ViewPagerFragmentDirections.actionViewPagerFragmentToNewsdetail(
                    currentNews
                ))


            }
            if (currentNews.fields.thumbnail == null) {
                holder.ivImage.setVisibility(GONE)
            } else {
                holder.ivImage.setVisibility(VISIBLE)
                Glide.with(holder.ivImage.context).load(currentNews.fields.thumbnail)
                    .into(holder.thumbnailImageCardView)
            }
            holder.shareImageCard.setOnClickListener {
                shareData(currentNews,holder.shareImageCard.context)
            }
        }


        private fun getDateInMillis(formattedDate: String): Long {
            return try {
                SimpleDateFormat("MMM d, yyyy  h:mm a").parse(formattedDate).getTime()
            } catch (e: ParseException) {
                e.printStackTrace()
                0
            }
        }

        private fun getTimeDifference(formattedDate: String): CharSequence? {
            return DateUtils.getRelativeTimeSpanString(
                getDateInMillis(formattedDate),
                System.currentTimeMillis(),
                1000
            )
        }


        fun shareData(news: NewsItem,context:Context) {
            val sharingIntent = Intent("android.intent.action.SEND")
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(
                "android.intent.extra.TEXT",
                news.webTitle.toString() + " : " + news.webUrl
            )

            context.startActivity(
                Intent.createChooser(
                    sharingIntent,
                    context.getString(R.string.share_article)
                )
            )
        }

        private fun formatDate(dateStringUTC: String): String? {
            var dateObject: Date? = null
            try {
                dateObject = SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'").parse(dateStringUTC)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val df = SimpleDateFormat("MMM d, yyyy  h:mm a", Locale.ENGLISH)
            val formattedDateUTC: String = df.format(dateObject)
            df.setTimeZone(TimeZone.getTimeZone("UTC"))
            var date: Date? = null
            try {
                date = df.parse(formattedDateUTC)
                df.setTimeZone(TimeZone.getDefault())
            } catch (e2: ParseException) {
                e2.printStackTrace()
            }
            return df.format(date)
        }




    }

    class NewsDiffUtil : DiffUtil.ItemCallback<NewsItem>() {
        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem == newItem
        }

    }


//    private fun setColorTheme(holder: ViewHolder) {
//        val colorTheme: String = this.sharedPrefs.getString(
//            this.mContext.getString(C0519R.string.settings_color_key),
//            this.mContext.getString(C0519R.string.settings_color_default)
//        )
//        if (colorTheme == this.mContext.getString(C0519R.string.settings_color_white_value)) {
//            holder.titleTextView.setBackgroundResource(C0519R.color.white)
//            holder.titleTextView.setTextColor(ViewCompat.MEASURED_STATE_MASK)
//        } else if (colorTheme == this.mContext.getString(C0519R.string.settings_color_sky_blue_value)) {
//            holder.titleTextView.setBackgroundResource(C0519R.color.nav_bar_start)
//            holder.titleTextView.setTextColor(-1)
//        } else if (colorTheme == this.mContext.getString(C0519R.string.settings_color_dark_blue_value)) {
//            holder.titleTextView.setBackgroundResource(C0519R.color.color_app_bar_text)
//            holder.titleTextView.setTextColor(-1)
//        } else if (colorTheme == this.mContext.getString(C0519R.string.settings_color_violet_value)) {
//            holder.titleTextView.setBackgroundResource(C0519R.color.violet)
//            holder.titleTextView.setTextColor(-1)
//        } else if (colorTheme == this.mContext.getString(C0519R.string.settings_color_light_green_value)) {
//            holder.titleTextView.setBackgroundResource(C0519R.color.light_green)
//            holder.titleTextView.setTextColor(-1)
//        } else if (colorTheme == this.mContext.getString(C0519R.string.settings_color_green_value)) {
//            holder.titleTextView.setBackgroundResource(C0519R.color.color_section)
//            holder.titleTextView.setTextColor(-1)
//        }
//    }
//
//    private fun setTextSize(holder: ViewHolder) {
//        val textSize: String = this.sharedPrefs.getString(
//            this.mContext.getString(C0519R.string.settings_text_size_key),
//            this.mContext.getString(C0519R.string.settings_text_size_default)
//        )
//        if (textSize == this.mContext.getString(C0519R.string.settings_text_size_medium_value)) {
//            holder.titleTextView.setTextSize(
//                0,
//                this.mContext.getResources().getDimension(C0519R.dimen.sp22)
//            )
//            holder.sectionTextView.setTextSize(
//                0,
//                this.mContext.getResources().getDimension(C0519R.dimen.sp14)
//            )
//            holder.trailTextView.setTextSize(
//                0,
//                this.mContext.getResources().getDimension(C0519R.dimen.sp16)
//            )
//            holder.authorTextView.setTextSize(
//                0,
//                this.mContext.getResources().getDimension(C0519R.dimen.sp14)
//            )
//            holder.dateTextView.setTextSize(
//                0,
//                this.mContext.getResources().getDimension(C0519R.dimen.sp14)
//            )
//        } else if (textSize == this.mContext.getString(C0519R.string.settings_text_size_small_value)) {
//            holder.titleTextView.setTextSize(
//                0,
//                this.mContext.getResources().getDimension(C0519R.dimen.sp20)
//            )
//            holder.sectionTextView.setTextSize(
//                0,
//                this.mContext.getResources().getDimension(C0519R.dimen.sp12)
//            )
//            holder.trailTextView.setTextSize(
//                0,
//                this.mContext.getResources().getDimension(C0519R.dimen.sp14)
//            )
//            holder.authorTextView.setTextSize(
//                0,
//                this.mContext.getResources().getDimension(C0519R.dimen.sp12)
//            )
//            holder.dateTextView.setTextSize(
//                0,
//                this.mContext.getResources().getDimension(C0519R.dimen.sp12)
//            )
//        } else if (textSize == this.mContext.getString(C0519R.string.settings_text_size_large_value)) {
//            holder.titleTextView.setTextSize(
//                0,
//                this.mContext.getResources().getDimension(C0519R.dimen.sp24)
//            )
//            holder.sectionTextView.setTextSize(
//                0,
//                this.mContext.getResources().getDimension(C0519R.dimen.sp16)
//            )
//            holder.trailTextView.setTextSize(
//                0,
//                this.mContext.getResources().getDimension(C0519R.dimen.sp18)
//            )
//            holder.authorTextView.setTextSize(
//                0,
//                this.mContext.getResources().getDimension(C0519R.dimen.sp16)
//            )
//            holder.dateTextView.setTextSize(
//                0,
//                this.mContext.getResources().getDimension(C0519R.dimen.sp16)
//            )
//        }
//    }
//
    /* access modifiers changed from: private */

//
//    fun clearAll() {
//        this.mNewsList.clear()
//        notifyDataSetChanged()
//    }
//
//    fun addAll(newsList: List<News?>?) {
//        this.mNewsList.clear()
//        this.mNewsList.addAll(newsList)
//        notifyDataSetChanged()
//    }
//

}

package com.example.newsappinkotlin.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappinkotlin.R
import com.example.newsappinkotlin.databinding.LvItemNewsBinding
import com.example.newsappinkotlin.home.Article
import com.example.newsappinkotlin.home.WebViewActivity


class HomeAdapter ( private var arrData :  ArrayList<Article>): RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {


    var context: Context? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val view : LvItemNewsBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
            R.layout.lv_item_news,parent,false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val a = arrData[position]
        val context = holder.itemView.context

       holder.binding.txtTitle.text = a.title
       holder.binding.txtSubtitle.text = a.author
       holder.binding.txtSubtitle2.text = a.publishedAt
       Glide.with(context).load(a.urlToImage).into(holder.binding.imgNews)

        // on click News Item goto WebActivity
        holder.binding.cLNews.setOnClickListener {
            val i = Intent(context, WebViewActivity::class.java)
            i.putExtra("url",a.url)
           context.startActivity(i)
        }
    }

    override fun getItemCount(): Int = if (arrData.isEmpty()) 0 else arrData.size


    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<Article>) {
        if (data.isEmpty()) {
            arrData = ArrayList()
        }
        arrData = data
        notifyDataSetChanged()
    }

    class MyViewHolder(var binding: LvItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root)
}
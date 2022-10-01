package com.example.newsappinkotlin.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappinkotlin.R
import com.example.newsappinkotlin.databinding.LvItemNewsBinding
import com.example.newsappinkotlin.home.Article


class HomeAdapter : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    private var arrData :  ArrayList<Article> = ArrayList()


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
    }

    override fun getItemCount(): Int = if (arrData.isNullOrEmpty()) 0 else arrData.size


    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<Article>) {
        if (data.isNullOrEmpty()) {
            arrData = ArrayList()
        }
        arrData = data
        notifyDataSetChanged()
    }

    class MyViewHolder(var binding: LvItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root)
}
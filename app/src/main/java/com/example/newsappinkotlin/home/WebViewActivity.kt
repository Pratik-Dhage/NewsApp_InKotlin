package com.example.newsappinkotlin.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.newsappinkotlin.R
import com.example.newsappinkotlin.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeFields()
    }

    private fun initializeFields() {

        binding = DataBindingUtil.setContentView(this,R.layout.activity_web_view)
        val url  = intent.getStringExtra("url")

        if (url != null) {
            binding.webViewNews.loadUrl(url)
        }
    }

}
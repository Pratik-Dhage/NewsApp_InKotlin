package com.example.newsappinkotlin.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.newsappinkotlin.R
import com.example.newsappinkotlin.databinding.ActivityLoginBinding
import com.example.newsappinkotlin.databinding.ActivityRegisterBinding
import com.example.newsappinkotlin.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var  view : View
    private var isSelected : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeFields()
        onClickListener()
    }

    private fun onClickListener() {
        binding.txtSignUp.setOnClickListener {
            val i = Intent(this,RegisterActivity::class.java)
            startActivity(i)
        }

        binding.imgCheckBox.setOnClickListener {

        }
    }

    private fun initializeFields() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        view= binding.root

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
package com.example.newsappinkotlin.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.newsappinkotlin.R
import com.example.newsappinkotlin.databinding.ActivityLoginBinding
import com.example.newsappinkotlin.databinding.ActivityRegisterBinding
import com.example.newsappinkotlin.helping_classes.Global
import com.example.newsappinkotlin.helping_classes.NetworkUtilities
import com.example.newsappinkotlin.helping_classes.SharedPreferenceHelper
import com.example.newsappinkotlin.home.HomeActivity
import com.example.newsappinkotlin.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var  view : View
    private var isUserLoggedIn : Boolean = false


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
            if(!binding.imgCheckBox2.isVisible) {
                binding.imgCheckBox.visibility = View.INVISIBLE
                binding.imgCheckBox2.isVisible = true
            }
            else{
                binding.imgCheckBox.isVisible = true
                binding.imgCheckBox2.visibility = View.INVISIBLE
            }
        }

        binding.imgCheckBox2.setOnClickListener {
            if(!binding.imgCheckBox2.isVisible) {
                binding.imgCheckBox.visibility = View.INVISIBLE
                binding.imgCheckBox2.isVisible = true
            }
            else{
                binding.imgCheckBox.isVisible = true
                binding.imgCheckBox2.visibility = View.INVISIBLE
            }
        }


        binding.btnSignIn.setOnClickListener {

           if( validations() )
           {
               if(NetworkUtilities.getConnectivityStatus(this)){
                   val i = Intent(this, HomeActivity::class.java)
                   i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                   startActivity(i)
               }
               else{
                   Global.showSnackBar(view,resources.getString(R.string.no_internet))
               }
           }

        }
    }

    private fun validations():Boolean {

        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPass.text.toString().trim()


        if (email.isEmpty()  ){
            Global.showSnackBar(view,resources.getString(R.string.email_error))
            return false
        }

            if (password.isEmpty()){
            Global.showSnackBar(view,resources.getString(R.string.password_error))
            return false
        }

        else
            SharedPreferenceHelper.writeString(this,"userEmail",email)
            SharedPreferenceHelper.writeString(this,"userPassword",password)

        return true

    }

    private fun initializeFields() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        view= binding.root

        val userEmail = getSharedPreferences("userEmail", Context.MODE_PRIVATE)
        val userPassword = getSharedPreferences("userPassword", Context.MODE_PRIVATE)

        if( userEmail!=null &&  userPassword!=null)
        {
            if(NetworkUtilities.getConnectivityStatus(this)){
                val i = Intent(this, HomeActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }
            else{
                Global.showSnackBar(view,resources.getString(R.string.no_internet))
            }
        }
    }




    override fun onBackPressed() {
        super.onBackPressed()
    }

}
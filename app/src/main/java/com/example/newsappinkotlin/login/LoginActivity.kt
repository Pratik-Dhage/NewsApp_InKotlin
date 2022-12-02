package com.example.newsappinkotlin.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.newsappinkotlin.R
import com.example.newsappinkotlin.databinding.ActivityLoginBinding
import com.example.newsappinkotlin.databinding.ActivityRegisterBinding
import com.example.newsappinkotlin.helping_classes.Global
import com.example.newsappinkotlin.helping_classes.NetworkUtilities
import com.example.newsappinkotlin.helping_classes.SharedPreferenceHelper
import com.example.newsappinkotlin.home.HomeActivity
import com.example.newsappinkotlin.home2.MainActivity
import com.example.newsappinkotlin.login.view_model.LoginViewModel
import com.example.newsappinkotlin.register.RegisterActivity
import com.example.newsappinkotlin.users.UserDao
import com.example.newsappinkotlin.users.UserDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var  view : View
    private lateinit var  viewModel : LoginViewModel
    private lateinit var userDao: UserDao
    private lateinit var userDB : UserDatabase


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
            if( validations() ) {

                val email = binding.edtEmail.text.toString().trim()
                val password = binding.edtPass.text.toString().trim()

                if(!userDao.isLogin(email,password)){

                    Global.showSnackBar(view,resources.getString(R.string.login_unsuccessfully))
                }
                else
                {
                    Global.showSnackBar(view,resources.getString(R.string.login_successfully))

                    val i = Intent(this,HomeActivity::class.java)
                    startActivity(i)

                }

               /*if(NetworkUtilities.getConnectivityStatus(this)){
                   val i = Intent(this, MainActivity::class.java)
                 //  i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                   startActivity(i)
               }
               else{
                   Global.showSnackBar(view,resources.getString(R.string.no_internet))
               }*/
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

             //   callLoginApi()
        return true

    }

    private fun initializeFields() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        view= binding.root
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]


        userDB = Room.databaseBuilder(this, UserDatabase::class.java,"userTable")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        userDao = userDB.getDao()

    }

    private fun callLoginApi() {
        if (NetworkUtilities.getConnectivityStatus(this@LoginActivity)) {
            viewModel.loginUserApi(this)
        } else {
            Global.showSnackBar(view, resources.getString(R.string.no_internet))
        }
    }


    override fun onBackPressed() {
      //  super.onBackPressed()
    }

}
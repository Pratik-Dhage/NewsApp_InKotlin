package com.example.newsappinkotlin.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.ContentInfoCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsappinkotlin.R
import com.example.newsappinkotlin.databinding.ActivityRegisterBinding
import com.example.newsappinkotlin.helping_classes.Global
import com.example.newsappinkotlin.helping_classes.Global.saveStringInSharedPref
import com.example.newsappinkotlin.helping_classes.NetworkUtilities
import com.example.newsappinkotlin.helping_classes.SharedPreferenceHelper
import com.example.newsappinkotlin.home.HomeActivity
import com.example.newsappinkotlin.login.LoginActivity
import com.example.newsappinkotlin.register.view_model.RegisterViewModel
import com.example.newsappinkotlin.users.UserDao
import com.example.newsappinkotlin.users.UserDatabase
import com.example.newsappinkotlin.users.UserTable

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var  view : View
    private lateinit var viewModel : RegisterViewModel
    private lateinit var userDB : UserDatabase
    private lateinit var userDao : UserDao
    var isAllowed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeFields()
        onClickListener()
    }

    private fun onClickListener() {
        binding.txtSignIn.setOnClickListener {
            val i = Intent(this,LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(i)
        }

        binding.btnSignUp.setOnClickListener {
          if(validations()) {

              //if validation is true then email and password will be added to UserTable
              val email = binding.edtEmail.text.toString().trim()
              val password = binding.edtPass.text.toString().trim()
              if(!userDao.isTaken(email))
              {
                  val userTable  = UserTable(0,email,password)
                  userDao.insertUser(userTable)

                  Global.showSnackBar(view,resources.getString(R.string.user_added_successfully))
              }
              else{
                  Global.showSnackBar(view,resources.getString(R.string.email_already_taken))
              }

             /* if(NetworkUtilities.getConnectivityStatus(this)){

                //  onRegisterApi()
                  val i = Intent(this, HomeActivity::class.java)
                  i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                  startActivity(i)
              }
              else{
                  Global.showSnackBar(view,resources.getString(R.string.no_internet))
              }*/

          }
            else{
              Global.showSnackBar(view,resources.getString(R.string.something_went_wrong_error))
            }

           }


    }

    private fun initializeFields() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register)
        view= binding.root
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        userDB = Room.databaseBuilder(this,UserDatabase::class.java,"userTable")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        userDao = userDB.getDao()
    }

    private fun validations() : Boolean{

        val fullName =  binding.edtFullName.text.toString().trim()
        val email =  binding.edtEmail.text.toString().trim()
        val password =  binding.edtPass.text.toString().trim()
        val mobileNumber =  binding.edtMobile.text.toString().trim()

        if(fullName.isEmpty()) {
            Global.showSnackBar(view,resources.getString(R.string.fullname_error))
            return false
        }
         if (email.isEmpty() ){
            Global.showSnackBar(view,resources.getString(R.string.email_error))
            return false
        }
        if (password.isEmpty()){
            Global.showSnackBar(view,resources.getString(R.string.password_error))
            return false
        }
        if (mobileNumber.isEmpty() || mobileNumber.length<10){
            Global.showSnackBar(view,resources.getString(R.string.phone_error))
            return false
        }

      else {
          //  saveStringInSharedPref(this,"email",email)
         //   saveStringInSharedPref(this,"pass",password)
            return true }
    }

    private fun onRegisterApi(){
        viewModel.strFullName = binding.edtFullName.text.toString().trim()
        viewModel.strEmail = binding.edtEmail.text.toString().trim()
        viewModel.strPass =  binding.edtPass.text.toString().trim()
        viewModel.strMobile =  binding.edtMobile.text.toString().trim()

    }


    override fun onBackPressed() {
       // super.onBackPressed() // onBackPressed button disabled for Register Activity
    }

}
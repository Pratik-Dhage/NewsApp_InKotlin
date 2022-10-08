package com.example.newsappinkotlin.register.view_model

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.example.newsappinkotlin.databinding.ActivityRegisterBinding
import com.example.newsappinkotlin.helping_classes.Global

class RegisterViewModel : ViewModel() {


 var  strFullName : String =""
 var strEmail : String =""
 var strPass : String =""
 var strMobile : String =""


    fun checkValidation() : Int{
       var isAllOk = 0

        when {
            strFullName.isEmpty() -> isAllOk = 1
            strEmail.isEmpty() || Global.isValidEmail(strEmail) -> isAllOk = 2
            strPass.isEmpty()   ->   isAllOk = 3
            strMobile.isEmpty()  -> isAllOk = 4
        }
      return isAllOk
    }

}
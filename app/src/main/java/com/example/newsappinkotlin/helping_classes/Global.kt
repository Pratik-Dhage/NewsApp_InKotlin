package com.example.newsappinkotlin.helping_classes

import android.content.Context
import android.text.Layout
import android.text.TextUtils
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import com.example.newsappinkotlin.R
import com.example.newsappinkotlin.api_manager.RestClient
import com.google.android.material.snackbar.Snackbar
import retrofit2.Retrofit


object Global {

    val api_Service by lazy {
       RestClient.create()
    }

    fun showToast(context: Context, str:String){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(view: View, str : String){
        val snackBar : Snackbar = Snackbar.make(view,str,Snackbar.LENGTH_SHORT)
        snackBar.show()
    }

    fun isValidEmail(target: CharSequence): Boolean =
        !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()

    fun isValidCellPhone(number: String?): Boolean =
        Patterns.PHONE.matcher(number.toString()).matches()



}
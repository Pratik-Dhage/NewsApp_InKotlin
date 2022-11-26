package com.example.newsappinkotlin.login.view_model

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsappinkotlin.api_manager.WebServices
import com.example.newsappinkotlin.helping_classes.Constants
import com.example.newsappinkotlin.helping_classes.Global
import com.example.newsappinkotlin.login.model.LoginRequestModel
import com.example.newsappinkotlin.login.model.LoginResponseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel : ViewModel() {

    private lateinit var subscription: Disposable
    var mutResponseError: MutableLiveData<String> = MutableLiveData()
    val mutResponseSuccess: MutableLiveData<LoginResponseModel> = MutableLiveData()


    var strEmail = ""
    var strPassword = ""

    //Login API
    fun loginUserApi(context: Activity) {
        val request = LoginRequestModel(
           "",
           "",
           "",
           "",
            strEmail,
            "",
            strPassword
        )

        subscription = Global.api_Service.loginUserApi(WebServices.LoginWs + Global.getLanguage(context), request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
            .subscribe({ result -> onApiSuccess(result)},
                { error -> onApiError(error)})

    }

    private fun onApiError(error: Throwable?) {
        if(error != null) {
            mutResponseError.value = error.localizedMessage
        }
    }

    private fun onApiSuccess(result: LoginResponseModel?) {
        mutResponseSuccess.value = result!!
    }


}
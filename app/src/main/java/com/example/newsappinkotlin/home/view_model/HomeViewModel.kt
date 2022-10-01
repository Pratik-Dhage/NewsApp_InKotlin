package com.example.newsappinkotlin.home.view_model

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsappinkotlin.api_manager.WebServices
import com.example.newsappinkotlin.helping_classes.Global
import com.example.newsappinkotlin.home.Article
import com.example.newsappinkotlin.home.HomeResponse
import com.example.newsappinkotlin.home.adapter.HomeAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel : ViewModel() {
private lateinit var subscription : Disposable
val mutHomeResponseApi : MutableLiveData<HomeResponse> = MutableLiveData()
val mutErrorResponse : MutableLiveData<String> = MutableLiveData()

var arrListData = ArrayList<Article>()
var adapterHome : HomeAdapter = HomeAdapter()
fun updateNewsData(){
    adapterHome.setData(arrListData)
}


 //Home News Api

 fun getHomeNewsData(context : Activity){
     subscription = Global.api_Service.homeNewsApi(WebServices.about_tesla+WebServices.api_key)
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .unsubscribeOn(Schedulers.io())
             .subscribe({ result -> onHomeApiSuccess(result) },
                 { error -> onApiError(error) })

     println("Here: @GET "+WebServices.Domain+WebServices.about_tesla+WebServices.api_key)
 }

   private fun onHomeApiSuccess(result : HomeResponse){
      mutHomeResponseApi.value = result
   }

    private fun onApiError(error : Throwable){
        mutErrorResponse.value = error.localizedMessage
    }
}
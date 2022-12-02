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
var adapterHome : HomeAdapter = HomeAdapter(arrListData)
fun updateNewsData(){
    adapterHome.setData(arrListData)
}


 //Home News Api

 fun getHomeNewsData(context : Activity){
     subscription = Global.api_Service.homeNewsApi(WebServices.top_headlines+WebServices.country_india+WebServices.api_key)
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .unsubscribeOn(Schedulers.io())
             .subscribe({ result -> onHomeApiSuccess(result) },
                 { error -> onApiError(error) })

 }

  //for left Navigation view
  fun getDifferentCountryNewsData(context : Activity,country:String){

      if(country == "us"){
          subscription = Global.api_Service.homeNewsApi(WebServices.top_headlines+WebServices.country_us+WebServices.api_key)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .unsubscribeOn(Schedulers.io())
              .subscribe({ result -> onHomeApiSuccess(result) },
                  { error -> onApiError(error) })
      }
      if(country == "ua"){
          subscription = Global.api_Service.homeNewsApi(WebServices.top_headlines+WebServices.country_ua+WebServices.api_key)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .unsubscribeOn(Schedulers.io())
              .subscribe({ result -> onHomeApiSuccess(result) },
                  { error -> onApiError(error) })
      }

  }


   private fun onHomeApiSuccess(result : HomeResponse){
      mutHomeResponseApi.value = result
   }

    private fun onApiError(error : Throwable){
        mutErrorResponse.value = error.localizedMessage
    }
}
package com.example.newsappinkotlin.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappinkotlin.R
import com.example.newsappinkotlin.databinding.ActivityHomeBinding
import com.example.newsappinkotlin.helping_classes.Global
import com.example.newsappinkotlin.helping_classes.NetworkUtilities
import com.example.newsappinkotlin.helping_classes.SharedPreferenceHelper
import com.example.newsappinkotlin.home.adapter.HomeAdapter
import com.example.newsappinkotlin.home.view_model.HomeViewModel
import com.example.newsappinkotlin.login.LoginActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    lateinit var view: View
    private lateinit var viewModel: HomeViewModel
    var isSwipeRefreshing: Boolean = false
    private lateinit var list : ArrayList<Article>
   

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeFields()
        setUpRecyclerViewData()

        if(NetworkUtilities.getConnectivityStatus(this))
        {   initObserver() }
        else { Global.showSnackBar(view,resources.getString(R.string.no_internet))      }

        callHomeNewsApi()
        onClickListeners()

    }

    private fun initializeFields() {

        binding = DataBindingUtil.setContentView(this@HomeActivity, R.layout.activity_home)
        view = binding.root //for snackBar
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.viewModel = viewModel


    }

    private fun initObserver() {

        //Home News Api Response
        viewModel.mutHomeResponseApi.observe(this@HomeActivity) {

           // Global.showToast(this,it.status)

            if (it.status == "ok") {
                list.addAll(it.articles)
              //  Global.showToast(this,resources.getString(R.string.loading))

                if (it.totalResults > 0) {
                    Global.showSnackBar(view,resources.getString(R.string.loading))
                    viewModel.arrListData.addAll(it.articles)
                    list.addAll(it.articles)

                    //setup Recyclerview

                    val recyclerView = binding.recyclerViewMain
                    viewModel.updateNewsData()
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.adapter = HomeAdapter(list)
                }
            }

        }

        // Api Error Handler
        viewModel.mutErrorResponse.observe(this@HomeActivity) {
            if (!it.isNullOrEmpty()) {
                Global.showSnackBar(view, it.toString())
                println("Here:+"+it.toString())
            } else {
                Global.showSnackBar(view, resources.getString(R.string.connection_error))
            }
        }
    }

    private fun swipeRefresher() {
        //  binding.swipeRefreshLayout.setOnRefreshListener {
        callHomeNewsApi()
    }

    private fun onClickListeners(){
       /* binding.recyclerViewMain.setOnClickListener {
          //  Global.showSnackBar(view,"Clicked")
            Global.showToast(this,"Clicked")
        }*/

    }

    private fun setUpRecyclerViewData() {

        list = arrayListOf<Article>()

        val recyclerView = binding.recyclerViewMain
        viewModel.updateNewsData()
        val layoutManager = LinearLayoutManager(this@HomeActivity, RecyclerView.VERTICAL, false)
        binding.recyclerViewMain.isVisible = true
        recyclerView.layoutManager = layoutManager
       recyclerView.adapter = HomeAdapter(list)

    }

    private fun callHomeNewsApi() {
        if (NetworkUtilities.getConnectivityStatus(this@HomeActivity)) {
            viewModel.getHomeNewsData(this@HomeActivity)
        } else
            Global.showSnackBar(view, resources.getString(R.string.no_internet))
    }


 /*     private fun onMenuClick(){

          binding.leftNavView.setNavigationItemSelectedListener{

              when(it.itemId){

                    R.id.nav_us->{
                        val country = "us"
                        viewModel.getDifferentCountryNewsData(this,country)

                        true
                    }

                  R.id.nav_ua->{
                      val country = "ua"
                      viewModel.getDifferentCountryNewsData(this,country)
                      true
                  }

                  else-> false
              }


          }

      }*/




    override fun onBackPressed() {
        //super.onBackPressed()
    }
}
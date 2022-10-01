package com.example.newsappinkotlin.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappinkotlin.R
import com.example.newsappinkotlin.databinding.ActivityHomeBinding
import com.example.newsappinkotlin.helping_classes.Global
import com.example.newsappinkotlin.helping_classes.NetworkUtilities
import com.example.newsappinkotlin.home.adapter.HomeAdapter
import com.example.newsappinkotlin.home.view_model.HomeViewModel as HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    var isSwipeRefreshing : Boolean = false
    var view : View = binding as View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeFields()
        initObserver()
        callHomeNewsApi()
    }

    private fun initializeFields() {

        binding = DataBindingUtil.setContentView(this@HomeActivity, R.layout.activity_home)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.viewModel = viewModel
        setUpRecyclerViewData()


    }

    private fun initObserver() {

        //Home News Api Response
        viewModel.mutHomeResponseApi.observe(this@HomeActivity) {


            if (it.status == "ok") {
                // swipeRefresher()
                //  viewModel.arrListData.clear()  // first clear the arrListData
                if (it.totalResults > 0) {
                    viewModel.arrListData.addAll(it.articles)
                }
            }

        }

        // Api Error Handler
        viewModel.mutErrorResponse.observe(this@HomeActivity) {
            if (!it.isNullOrEmpty()) {
                Global.showToast(this@HomeActivity, it.toString())
            } else {
                Global.showToast(this, resources.getString(R.string.connection_error))
            }
        }
    }

    private fun swipeRefresher() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            callHomeNewsApi()
        }
    }

    private fun setUpRecyclerViewData() {
        val layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewMain.layoutManager = layoutManager
        binding.recyclerViewMain.adapter = HomeAdapter()
        viewModel.updateNewsData()
    }

    private fun callHomeNewsApi() {
        if (NetworkUtilities.getConnectivityStatus(this@HomeActivity)) {
            viewModel.getHomeNewsData(this@HomeActivity)
        } else
          //  Global.showToast(this, resources.getString(R.string.no_internet))
        Global.showSnackBar(view,resources.getString(R.string.no_internet))
    }
}
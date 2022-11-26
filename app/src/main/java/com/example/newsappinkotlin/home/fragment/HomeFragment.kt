package com.example.newsappinkotlin.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappinkotlin.R
import com.example.newsappinkotlin.databinding.FragmentHomeBinding
import com.example.newsappinkotlin.helping_classes.Global
import com.example.newsappinkotlin.helping_classes.NetworkUtilities
import com.example.newsappinkotlin.home.Article
import com.example.newsappinkotlin.home.HomeActivity
import com.example.newsappinkotlin.home.adapter.HomeAdapter
import com.example.newsappinkotlin.home.view_model.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var mActivity: HomeActivity
    private var binding: FragmentHomeBinding? = null
    private lateinit var list : ArrayList<Article>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as HomeActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mActivity = activity as HomeActivity
        viewModel = ViewModelProvider(mActivity).get(HomeViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding!!.viewModel = viewModel
     val  view =  binding!!.root
       // view = binding.root //for snackBar

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        setUpRecyclerViewData()
        callHomeNewsApi()
        onClickListeners()
    }

    private fun initObserver() {

        //Home News Api Response
        viewModel.mutHomeResponseApi.observe(mActivity) {

            if (it.status == "ok") {
                list.addAll(it.articles)
                Global.showToast(mActivity,"All Good")
                // swipeRefresher()
                //  viewModel.arrListData.clear()  // first clear the arrListData
                if (it.totalResults > 0) {
                    Global.showToast(mActivity,""+it.totalResults.toString())
                    viewModel.arrListData.addAll(it.articles)
                   // setUpRecyclerViewData()
                }
            }

        }

        // Api Error Handler
        viewModel.mutErrorResponse.observe(mActivity) {
            if (!it.isNullOrEmpty()) {
                Global.showToast(mActivity, it.toString())
            } else {
                Global.showToast(mActivity, resources.getString(R.string.connection_error))
            }
        }
    }

    private fun setUpRecyclerViewData() {

        list = arrayListOf<Article>()

        val recyclerView = binding?.recyclerViewMain
        viewModel.updateNewsData()
        val layoutManager = LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = HomeAdapter(list)

    }

    private fun callHomeNewsApi() {
        if (NetworkUtilities.getConnectivityStatus(mActivity)) {
            viewModel.getHomeNewsData(mActivity)
        } else
            Global.showSnackBar(binding!!.root, resources.getString(R.string.no_internet))
    }

    private fun onClickListeners(){
        binding?.recyclerViewMain?.setOnClickListener {
            //  Global.showSnackBar(view,"Clicked")
            Global.showToast(mActivity,"Clicked")
        }


}

}

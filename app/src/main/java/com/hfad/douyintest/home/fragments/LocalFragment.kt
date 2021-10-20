package com.hfad.douyintest.home.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hfad.douyintest.databinding.FragmentLocalBinding
import com.hfad.douyintest.home.adapter.VideoListAdapter
import com.hfad.douyintest.home.beans.DataCreate
import com.hfad.douyintest.home.beans.VideoBean

class LocalFragment : Fragment() {

    private lateinit var bindingLocal: FragmentLocalBinding
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var rvLocal: RecyclerView
    private lateinit var mDatas: ArrayList<VideoBean>
    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingLocal = FragmentLocalBinding.inflate(inflater, container, false)
        //refreshLayout = bindingLocal.refreshLayoutLocal
        rvLocal = bindingLocal.rvLocal
        return bindingLocal.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContext = activity!!

        DataCreate.initData();

        //val linearLayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        //val gridLayoutManager = GridLayoutManager(mContext, 2)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        rvLocal.layoutManager = staggeredGridLayoutManager
        rvLocal.adapter = VideoListAdapter(mContext, DataCreate.datas)
    }


}
package com.hfad.douyintest.home.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hfad.douyintest.activities.VideoPlayActivity
import com.hfad.douyintest.databinding.FragmentLocalBinding
import com.hfad.douyintest.home.adapter.VideoListAdapter
import com.hfad.douyintest.home.beans.DataCreate
import com.hfad.douyintest.home.beans.VideoBean
import com.hfad.douyintest.utils.ActivityUtils

/**
 *
 */
class LocalFragment : Fragment() {

    private lateinit var bindingLocal: FragmentLocalBinding
    //private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var rvLocal: RecyclerView
    private lateinit var mDatas: ArrayList<VideoBean>
    private val mContext: Context by lazy { activity!! }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        bindingLocal = FragmentLocalBinding.inflate(inflater, container, false)
        //refreshLayout = bindingLocal.refreshLayoutLocal  只用于网络数据请求
        rvLocal = bindingLocal.rvLocal
        return bindingLocal.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataCreate.initData()  //创建固定测试数据源
        //val linearLayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        //val gridLayoutManager = GridLayoutManager(mContext, 2)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvLocal.layoutManager = staggeredGridLayoutManager

        mDatas = DataCreate.datas   //可自设置网络请求数据

        val videoListAdapter = VideoListAdapter(mContext, mDatas)
        rvLocal.adapter = videoListAdapter
        videoListAdapter.setOnVideoItemClickListener(object: VideoListAdapter.OnVideoItemClickListener{
            override fun onVideoItemClick(position: Int) {
                VideoPlayActivity.posPlay = position
                ActivityUtils.switchToVideoPlayActivity(mContext, VideoPlayActivity::class.java, "")
            }
        })
    }
}
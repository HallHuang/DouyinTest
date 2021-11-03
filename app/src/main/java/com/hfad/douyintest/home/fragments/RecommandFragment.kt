package com.hfad.douyintest.home.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hfad.douyintest.R
import com.hfad.douyintest.activities.SearchActivity
import com.hfad.douyintest.databinding.FragmentRecommandBinding
import com.hfad.douyintest.home.HomeViewModel
import com.hfad.douyintest.home.adapter.TiktokVideoAdapter
import com.hfad.douyintest.home.beans.DataCreate
import com.hfad.douyintest.home.view.*
import com.hfad.douyintest.utils.ActivityUtils

/**
 *基于固定数据源+RecyclerView+ViewPagerLinearLayoutManager(SnapPageHelper)+自定义集成子视图的类viewpager视频竖直方向活动切换框架
 */
class RecommandFragment : Fragment() {

    private lateinit var seekBarVideo: SeekBar
    private lateinit var ivPlay: ImageView
    private lateinit var containerView: RelativeLayout
    private lateinit var ivCurcover: ImageView
    private lateinit var homeViewModel: HomeViewModel
    private var videoView: FullScreenVideoView? = null
    private lateinit var layoutManager: ViewPagerLinearLayoutManager
    private lateinit var refreshVideoLayout: SwipeRefreshLayout
    private lateinit var recyclerViewVideo: RecyclerView
    private lateinit var binding: FragmentRecommandBinding
    private val mContext: Context by lazy { activity!! }
    private var oldPos: Int = 0
    private var curPos: Int = -1

    companion object {
        private const val UPDATEBAR = 0
    }

    private lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRecommandBinding.inflate(inflater, container, false)
        recyclerViewVideo = binding.recyclerViewVideo
        refreshVideoLayout = binding.refreshVideoLayout     //主要用于网络数据请求，不做具体动作实现
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tiktokVideoAdapter = TiktokVideoAdapter(mContext, DataCreate.datas)
        recyclerViewVideo.adapter = tiktokVideoAdapter
        recyclerViewVideo.setItemViewCacheSize(3)

        layoutManager = ViewPagerLinearLayoutManager(mContext)
        recyclerViewVideo.layoutManager = layoutManager

        layoutManager.setOnViewPagerListener(object :
            ViewPagerLinearLayoutManager.OnViewPagerListener {
            override fun onInitCompleted() {
                playVideoAtPos(0)  //由于数据源固定，将首先播放第一个视频
            }

            override fun onPageRelease(isNext: Boolean, position: Int) {
                ivCurcover.visibility = View.GONE
            }

            override fun onPageSelected(position: Int, isBottom: Boolean) {
                oldPos = position
                playVideoAtPos(position)
                if (isBottom) {
                    //
                }
            }
        })

        homeViewModel = ViewModelProvider(activity!!).get(HomeViewModel::class.java)
        homeViewModel.isVideoFragment.observe(viewLifecycleOwner, {
            if (!it) {
                containerView.removeView(videoView)  //底部导航栏切换
            }
        })

        //每隔0.5s进行进度更新
        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == UPDATEBAR) {
                    val currentPosition = videoView?.currentPosition!!
                    seekBarVideo.progress = currentPosition
                    seekBarVideo.max = videoView!!.duration
                    handler.sendEmptyMessageDelayed(UPDATEBAR, 500)
                }
            }
        }
    }

    private fun playVideoAtPos(posPlay: Int) {

        curPos = posPlay

        //用户中心界面的数据更新
        homeViewModel.curUserBeanData.postValue(DataCreate.datas[posPlay].userBean)

        //查询特定位置的子视图实例
        val itemView = layoutManager.findViewByPosition(posPlay)
        if (itemView == null) {
            return
        }
        containerView = itemView.findViewById(R.id.rl_container)
        val pauseAndLikeView = containerView.findViewById<PauseAndLikeView>(R.id.view_pause_like)
        val ivCover = containerView.findViewById<ImageView>(R.id.iv_cover_video)
        ivPlay = containerView.findViewById(R.id.iv_play)
        val viewInteract = containerView.findViewById<InteractView>(R.id.view_interact)
        videoView = containerView.findViewById(R.id.video_view)
        seekBarVideo = containerView.findViewById(R.id.seek_bar_video)

        //暂停双击界面的点击事件
        pauseAndLikeView.setOnPlayPauseClickListener(object :
            PauseAndLikeView.OnPlayPauseClickListener {
            override fun onPlayOrPause() {
                if (videoView != null) {
                    if (videoView!!.isPlaying) {
                        videoView?.pause()
                        ivPlay.visibility = View.VISIBLE
                    } else {
                        videoView?.start()
                        ivPlay.visibility = View.GONE
                    }
                }
            }

            override fun onDoubleClickLike() {
                //点赞动作相关网络数据更新（视图层更新已在PauseAndLikeView:GestureDetector{}中实现）
            }

        })

        //视频交互界面的点击事件
        viewInteract.setOnVideoInfoClickListener(object : InteractView.OnVideoInfoClickListener {
            override fun onHeadClick() {
                homeViewModel.isBackToHome.postValue(false) //切换到用户界面
            }

            override fun onLikeClick() {
                //点赞动作相关网络数据更新（视图层更新已在InteractView：updateLikeView中实现）
                Toast.makeText(mContext, "LIKE!!", Toast.LENGTH_SHORT).show()
            }

            override fun onCommentClick() {
                Toast.makeText(mContext, "COMMENTS!!", Toast.LENGTH_SHORT).show()
            }

            override fun onShareClick() {
                Toast.makeText(mContext, "SHARE!!", Toast.LENGTH_SHORT).show()
            }

            override fun onFocusClick() {
                //关注动作相关网络数据更新（视图层更新已在InteractView：updateFocusView中实现）
            }

            override fun onTextClick(matchMode: MatchMode, matchedText: String) {
                //点击底部文本中的关键字段进行跳转
                when (matchMode) {
                    MatchMode.Mode_PERSON -> {
                        ActivityUtils.switchToSearchActivity(mContext,
                            SearchActivity::class.java,
                            matchedText)
                    }
                    MatchMode.MODE_TOPIC -> {
                        ActivityUtils.switchToSearchActivity(mContext,
                            SearchActivity::class.java,
                            matchedText)
                    }
                    MatchMode.MODE_URL -> {
                        ActivityUtils.switchToBrowser(mContext, matchedText)
                    }
                }
            }
        })

        //手动设置视频进度
        seekBarVideo.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                val progressSeek = p0?.progress!!
                if (videoView != null && videoView!!.isPlaying) {
                    handler.sendEmptyMessage(UPDATEBAR)
                    videoView!!.seekTo(progressSeek)
                }
            }
        })

        autoPlayVideo(posPlay, ivCover)

    }

    private fun autoPlayVideo(posPlay: Int, ivCover: ImageView) {
        val videoPath =
            "android.resource://" + activity?.packageName + "/" + DataCreate.datas[posPlay].videoRes
        videoView?.setVideoPath(videoPath)
        //网络环境下在线播放：videoView.setVideoURI(Uri.parse(videoNetPath))
        videoView?.start()
        handler.sendEmptyMessage(UPDATEBAR)
        ivPlay.visibility = View.GONE
        videoView?.setOnPreparedListener {
            it.isLooping = true
            ivCurcover = ivCover
            ivCurcover.visibility = View.GONE
        }
    }


    override fun onResume() {
        super.onResume()
        videoView?.start()
        ivPlay.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        videoView?.pause()
    }

    override fun onStop() {
        super.onStop()
        videoView?.stopPlayback()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (videoView != null) {
            videoView = null
        }
    }

}

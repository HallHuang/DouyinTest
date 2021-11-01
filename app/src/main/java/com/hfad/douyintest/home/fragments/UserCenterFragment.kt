package com.hfad.douyintest.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.hfad.douyintest.R
import com.hfad.douyintest.databinding.FragmentUserCenterBinding
import com.hfad.douyintest.home.HomeViewModel
import com.hfad.douyintest.home.adapter.CommonFragmentAdapter
import com.hfad.douyintest.home.beans.VideoBean
import com.hfad.douyintest.home.view.AttentionButton
import kotlin.math.abs

class UserCenterFragment : Fragment(), AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    private lateinit var tvFansCount: TextView
    private lateinit var tvFocusCount: TextView
    private lateinit var tvGetlikeCount: TextView
    private lateinit var tvSign: TextView
    private lateinit var tvNickname: TextView
    private lateinit var ivBg: ImageView
    private lateinit var btnFocus: AttentionButton
    private lateinit var binding: FragmentUserCenterBinding
    private lateinit var vpUserVideos: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var appbar: AppBarLayout
    private lateinit var header: ConstraintLayout
    private lateinit var toolBar: Toolbar
    private lateinit var ivBack: ImageView
    private lateinit var ivBackToolbar: ImageView

    private lateinit var homeViewModel: HomeViewModel

    private val userFragments: List<Fragment> by lazy {
        ArrayList<Fragment>().apply {
            this.add(UserWorksFragment())
            this.add(UserLikesFragment())
        }
    }

    private val titles = arrayListOf("作品", "喜欢")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUserCenterBinding.inflate(inflater, container, false)
        vpUserVideos = binding.vpUserVideos
        tabLayout = binding.tabLayoutVideos
        appbar = binding.appbarLayout
        header = binding.headerLayout.rootHeader
        ivBg = binding.headerLayout.ivBg
        tvNickname = binding.headerLayout.tvNickname
        tvSign = binding.headerLayout.tvSign
        tvGetlikeCount = binding.headerLayout.tvGetlikeCount
        tvFocusCount = binding.headerLayout.tvFocusCount
        tvFansCount = binding.headerLayout.tvFansCount
        toolBar = binding.toolbarUser
        ivBack = binding.headerLayout.ivBack
        ivBackToolbar = binding.ivBackToolbar
        btnFocus = binding.btnFocus
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(activity!!).get(HomeViewModel::class.java)

        homeViewModel.curUserBeanData.observe(viewLifecycleOwner, {
            setUserInfoData(it)
        })


        vpUserVideos.adapter = CommonFragmentAdapter(childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, userFragments, titles)

        tabLayout.setupWithViewPager(vpUserVideos)

        appbar.addOnOffsetChangedListener(this)  //滑动显示切换

        setActionBar()  //自定义横向布局标题栏设置

        setListener()

    }

    private fun setUserInfoData(userBean: VideoBean.UserBean) {
        btnFocus.setImage(userBean.head)
        ivBg.setImageResource(userBean.head)    //数据源限制，顶部背景图片和头像相同，网络资源下可以个性化设置
        tvNickname.text = userBean.nickName
        tvSign.text = userBean.sign
        tvGetlikeCount.text = userBean.likeCount.toString()
        tvFocusCount.text = userBean.fansCount.toString()
        tvFansCount.text = userBean.fansCount.toString()
        //其他信息省略载入，可自行补充

    }

    private fun setActionBar() {
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(toolBar)
        val supportActionBar = appCompatActivity.supportActionBar
        supportActionBar?.title = ""
    }

    private fun setListener() {
        ivBack.setOnClickListener(this)
        ivBackToolbar.setOnClickListener(this)
    }

    //滑动距离比 -> 标题栏toolbar和上端布局的显隐切换
    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val percent = abs(verticalOffset * 1.0f) / appBarLayout!!.totalScrollRange
        if (percent > 0.5) {
            toolBar.visibility = View.VISIBLE
            if (percent > 0.8){
                header.alpha = 0.0f//避免遮盖toolbar
            }
        } else {
            toolBar.visibility = View.GONE
            header.alpha = 1.0f
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.iv_back, R.id.iv_back_toolbar -> homeViewModel.isBackToHome.postValue(true)    //两个返回按钮的点击事件
        }
    }


}
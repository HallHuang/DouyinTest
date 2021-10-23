package com.hfad.douyintest.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import kotlin.math.abs

class UserCenterFragment : Fragment(), AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

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
        toolBar = binding.toolbarUser
        ivBack = binding.headerLayout.ivBack
        ivBackToolbar = binding.ivBackToolbar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(activity!!).get(HomeViewModel::class.java)
        vpUserVideos.adapter = CommonFragmentAdapter(childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, userFragments, titles)

        tabLayout.setupWithViewPager(vpUserVideos)

        appbar.addOnOffsetChangedListener(this)

        setListener()

        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(toolBar)
        val supportActionBar = appCompatActivity.supportActionBar
        supportActionBar?.title = ""

    }

    private fun setListener() {
        ivBack.setOnClickListener(this)
        ivBackToolbar.setOnClickListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val percent = abs(verticalOffset * 1.0f) / appBarLayout!!.totalScrollRange
        if (percent > 0.5) {
            toolBar.visibility = View.VISIBLE
            if (percent > 0.8){
                header.alpha = 0.0f
            }
        } else {
            toolBar.visibility = View.GONE
            header.alpha = 1.0f
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.iv_back, R.id.iv_back_toolbar -> homeViewModel.isBackToHome.postValue(true)
        }
    }


}
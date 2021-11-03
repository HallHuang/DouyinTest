package com.hfad.douyintest

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hfad.douyintest.databinding.ActivityMainBinding
import com.hfad.douyintest.fragments.FriendsFragment
import com.hfad.douyintest.fragments.HomeFragment
import com.hfad.douyintest.fragments.MeFragment
import com.hfad.douyintest.fragments.MessagesFragment
import com.hfad.douyintest.home.HomeViewModel

/**
 * 底部导航布局框架：点击项在线性布局内，界面在帧布局内
 * 通过FragmentTransaction进行界面切换，同时进行相应的内部显示文本格式切换
 */
class MainActivityKt : AppCompatActivity(), View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mainBinding: ActivityMainBinding   //采用viewBinding创建控件引用
    private lateinit var itemHome: RelativeLayout
    private lateinit var itemFriends: RelativeLayout
    private lateinit var itemShare: RelativeLayout
    private lateinit var itemMessage: RelativeLayout
    private lateinit var itemMe: RelativeLayout
    private lateinit var tvOne: TextView
    private lateinit var tvTwo: TextView
    private lateinit var tvThree: TextView
    private lateinit var tvFour: TextView
    private lateinit var llBottom: LinearLayout

    private lateinit var curItem: TextView   //当前点击项文本框对象
    private lateinit var lastItem: TextView  //前点击项文本框对象

    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        llBottom = mainBinding.bottomNavigation
        itemHome = mainBinding.itemOne
        tvOne = mainBinding.tvOne
        itemFriends = mainBinding.itemTwo
        tvTwo = mainBinding.tvTwo
        itemShare = mainBinding.itemThree
        itemMessage = mainBinding.itemFour
        tvThree = mainBinding.tvThree
        itemMe = mainBinding.itemFive
        tvFour = mainBinding.tvFour

        curItem = tvOne
        lastItem = tvOne
        currentFragment = HomeFragment()

        //统一设置点击监听器
        itemHome.setOnClickListener(this)
        itemFriends.setOnClickListener(this)
        itemShare.setOnClickListener(this)
        itemMessage.setOnClickListener(this)
        itemMe.setOnClickListener(this)

        //起始页面初始化突出显示
        switchToFragment(HomeFragment())
        tvOne.textSize = 20F
        tvOne.setTextColor(ContextCompat.getColor(this, R.color.white))

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    //底部导航点击项分类处理
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.item_one -> {
                lastItem = curItem
                curItem = tvOne
                llBottom.background = ContextCompat.getDrawable(this, R.color.black)
                switchTextViewStyle(lastItem, curItem)
                switchToFragment(HomeFragment())
                homeViewModel.isVideoFragment.postValue(true)   //视频切换标志变量赋值，在RecommandFragment进行变量变化响应处理
            }
            R.id.item_two -> {
                lastItem = curItem
                curItem = tvTwo
                llBottom.background =  ContextCompat.getDrawable(this, R.color.black)
                switchTextViewStyle(lastItem, curItem)
                switchToFragment(FriendsFragment())
                homeViewModel.isVideoFragment.postValue(false)
            }
            R.id.item_three -> {

            }
            R.id.item_four -> {
                lastItem = curItem
                curItem = tvThree
                llBottom.background = ContextCompat.getDrawable(this, R.color.white)
                switchTextViewStyle(lastItem, curItem)
                switchToFragment(MessagesFragment())
                homeViewModel.isVideoFragment.postValue(false)
            }
            R.id.item_five -> {
                lastItem = curItem
                curItem = tvFour
                llBottom.background = ContextCompat.getDrawable(this, R.color.white)
                switchTextViewStyle(lastItem, curItem)
                switchToFragment(MeFragment())
                homeViewModel.isVideoFragment.postValue(false)
            }
        }
    }

    //相邻时间点击项的文本格式切换（当前突出、原项默认）
    private fun switchTextViewStyle(textView1: TextView, textView2: TextView) {
        textView1.textSize = 17F
        textView1.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))

        //当前项文本突出显示格式
        textView2.textSize = 20F
        if (textView2 == tvThree || textView2 == tvFour) {
            textView2.setTextColor(ContextCompat.getColor(this, R.color.black))
        } else {
            textView2.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
    }

    private fun switchToFragment(targetFragment: Fragment) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        if (!targetFragment.isAdded) {
            beginTransaction.hide(currentFragment)
            beginTransaction.add(R.id.container_fragments, targetFragment, "targetFragment")
        } else {
            beginTransaction.hide(currentFragment).show(targetFragment)
        }
        currentFragment = targetFragment
        beginTransaction.commitAllowingStateLoss()
    }

}
package com.hfad.douyintest

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hfad.douyintest.databinding.ActivityMainBinding

class MainActivityKt : AppCompatActivity(), View.OnClickListener {

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

        //统一设置点击监听器
        itemHome.setOnClickListener(this)
        itemFriends.setOnClickListener(this)
        itemShare.setOnClickListener(this)
        itemMessage.setOnClickListener(this)
        itemMe.setOnClickListener(this)

        //起始页面初始化
        tvOne.textSize = 20F
        tvOne.setTextColor(resources.getColor(R.color.white))

    }

    //底部导航点击项分类处理
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.item_one -> {
                lastItem = curItem
                curItem = tvOne
                llBottom.background = resources.getDrawable(R.color.black, null)
                switchTextView(lastItem, curItem)
            }
            R.id.item_two -> {
                lastItem = curItem
                curItem = tvTwo
                llBottom.background = resources.getDrawable(R.color.black, null)
                switchTextView(lastItem, curItem)
            }
            R.id.item_three -> {

            }
            R.id.item_four -> {
                lastItem = curItem
                curItem = tvThree
                llBottom.background = resources.getDrawable(R.color.white, null)
                switchTextView(lastItem, curItem)
            }
            R.id.item_five -> {
                lastItem = curItem
                curItem = tvFour
                llBottom.background = resources.getDrawable(R.color.white, null)
                switchTextView(lastItem, curItem)
            }
        }
    }

    //相邻点击项的文本格式切换（当前突出、原项默认）
    private fun switchTextView(textView1: TextView, textView2: TextView) {
        textView1?.textSize = 17F
        textView1?.setTextColor(resources.getColor(android.R.color.darker_gray))

        //当前项文本突出显示格式
        textView2?.textSize = 20F
        if (textView2 == tvThree || textView2 == tvFour) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textView2?.setTextColor(resources.getColor(R.color.black, null))
            } else {
                textView2?.setTextColor(resources.getColor(R.color.black))
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textView2?.setTextColor(resources.getColor(R.color.white, null))
            } else {
                textView2?.setTextColor(resources.getColor(R.color.white))
            }
        }
    }


}
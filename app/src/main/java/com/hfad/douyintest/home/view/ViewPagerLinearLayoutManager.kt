package com.hfad.douyintest.home.view

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * 视频控件有三个状态：初始化，运行，销毁(INIT,SELECTED,RELEASE)，对应三个接口方法
 * 1 初始化/销毁： 子视图进入/离开父阵列布局时 ->
 *      recyclerview.addOnChildAttachStateChangeListener{onChildViewAttachedToWindow{INIT}; onChildViewDetachedFromWindow{RELEASE}}
 * 2 运行： 滚动状态为空闲时 ->
 *      linearLayoutManager.onScrollStateChanged(state: Int){*.IDLE -> SELECTED}
 */
class ViewPagerLinearLayoutManager(context: Context, orientation: Int, reverseLayout: Boolean)
    : LinearLayoutManager(context, orientation, reverseLayout) {

    private var deltaY: Int = 0
    private lateinit var onViewPagerListener: OnViewPagerListener
    private var recyclerView: RecyclerView? = null
    private var pagerSnapHelper: PagerSnapHelper

    constructor(context: Context): this(context, VERTICAL, false)

    init {
        pagerSnapHelper = PagerSnapHelper()
    }

    //布局管理器依附recyclerview时
    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        pagerSnapHelper.attachToRecyclerView(view)  //加入对齐帮助器
        this.recyclerView = view
        //子视图依附于recyclerview时
        recyclerView?.addOnChildAttachStateChangeListener(object: RecyclerView.OnChildAttachStateChangeListener{
            override fun onChildViewAttachedToWindow(view: View) {
                if (childCount == 1){
                    onViewPagerListener.onInitCompleted()
                }
            }

            override fun onChildViewDetachedFromWindow(view: View) {
                when(deltaY > 0){
                    true -> {
                        onViewPagerListener.onPageRelease(true, getPosition(view))
                    }
                    false -> {
                        onViewPagerListener.onPageRelease(false, getPosition(view))
                    }
                }
            }
        })
    }

    //RecyclerView滑动状态改变时的过程回调函数
    override fun onScrollStateChanged(state: Int) {
        when(state){
            RecyclerView.SCROLL_STATE_IDLE -> {//正常播放状态=非拖移状态=空闲状态=无任何手上动作时
                val viewIdle = pagerSnapHelper.findSnapView(this) ?: return
                val posIdle = getPosition(viewIdle)
                if (childCount == 1){
                    onViewPagerListener.onPageSelected(posIdle, posIdle == itemCount- 1)
                }
            }
        }
    }

    //竖直方向滑动时的过程回调，dy>0: 视图被上移滑动
    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        this.deltaY = dy
        return super.scrollVerticallyBy(dy, recycler, state)
    }

    fun setOnViewPagerListener(onViewPagerListener: OnViewPagerListener){
        this.onViewPagerListener = onViewPagerListener
    }

    interface OnViewPagerListener{
        fun onInitCompleted()
        fun onPageRelease(isNext: Boolean, position: Int)
        fun onPageSelected(position: Int, isBottom: Boolean)
    }
}
package com.hfad.douyintest.home.view

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.ImageView
import android.widget.RelativeLayout
import com.hfad.douyintest.R
import com.hfad.douyintest.utils.AnimUtils

/**
 * 暂停和双击点赞的中间辅助视图
 */
class PauseAndLikeView(context: Context, attributeSet: AttributeSet?) :
    RelativeLayout(context, attributeSet) {

    var gestureDetector: GestureDetector
    private lateinit var onPlayPauseClickListener: OnPlayPauseClickListener

    companion object {
        const val WIDTHLIKE = 300
        const val HEIGHTLIKE = 300
        val ANGLES = floatArrayOf(-30f, 0f, 30f)
    }

    constructor(context: Context) : this(context, null)

    init {
        gestureDetector = GestureDetector(object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                onPlayPauseClickListener.onPlayOrPause() //暂停播放事件在根片段中实现
                return true
            }

            override fun onDoubleTap(e: MotionEvent?): Boolean {
                addLikeView(e)  //双击点赞加载动画视图
                onPlayPauseClickListener.onDoubleClickLike()  //双击点击后的其他视图层和网络数据层对应处理，在根片段中实现
                return true
            }
        })

        setOnTouchListener(object: OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                gestureDetector.onTouchEvent(p1)
                return true
            }
        })
    }

    private fun addLikeView(e: MotionEvent?) {
        val imageView = ImageView(context)
        imageView.setImageResource(R.mipmap.ic_like)

        //点赞动画相对于点击位置的定位设置
        val layoutParams = LayoutParams(WIDTHLIKE, HEIGHTLIKE)
        layoutParams.leftMargin = (e?.getX()!! - WIDTHLIKE / 2).toInt()
        layoutParams.topMargin = (e.getY() - WIDTHLIKE ).toInt()
        imageView.layoutParams = layoutParams
        addView(imageView)  //添加图片到根视图
        startAnim(imageView)
    }

    //动画播放一次
    private fun startAnim(imageView: ImageView) {
        val animationSet = AnimationSet(true)
        val random = (0..2).random()
        val degreeTitlt = ANGLES[random]  //动画图片随机倾斜角度(-30，0，30)

        animationSet.addAnimation(AnimUtils.rotateAnim(0, 0, degreeTitlt))
        animationSet.addAnimation(AnimUtils.scaleAnim(100, 2f, 1f, 0))
        animationSet.addAnimation(AnimUtils.alphaAnim(0f, 1f, 100, 0))
        animationSet.addAnimation(AnimUtils.scaleAnim(500, 1f, 1.8f, 300))
        animationSet.addAnimation(AnimUtils.alphaAnim(1f, 0f, 500, 300))
        animationSet.addAnimation(AnimUtils.translationAnim(500, 0f, 0f, 0f, -400f, 300))

        animationSet.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                imageView.post {
                    removeView(imageView)   //动画结束后移除图片
                }
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })

        imageView.startAnimation(animationSet)  //图片对象启动动画集合
    }

    interface OnPlayPauseClickListener {
        fun onPlayOrPause()
        fun onDoubleClickLike()
    }

    /**
     * 设置单机播放/暂停事件&&双击点赞接口实例
     */
    fun setOnPlayPauseClickListener(onPlayPauseClickListener: OnPlayPauseClickListener) {
        this.onPlayPauseClickListener = onPlayPauseClickListener
    }

}
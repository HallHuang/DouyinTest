package com.hfad.douyintest.home.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.hfad.douyintest.R
import com.hfad.douyintest.activities.SearchActivity
import com.hfad.douyintest.databinding.ViewInteractBinding
import com.hfad.douyintest.home.beans.VideoBean
import com.hfad.douyintest.utils.ActivityUtils
import com.hfad.douyintest.utils.NumberUtils

class InteractView(context: Context, attrs: AttributeSet?)
    : RelativeLayout(context, attrs), View.OnClickListener {

    private var binding: ViewInteractBinding

    private lateinit var videoBean: VideoBean
    private lateinit var videoInfoListener: OnVideoInfoClickListener

    constructor(context: Context): this(context, null)

    init {
        binding = ViewInteractBinding.bind(View.inflate(context, R.layout.view_interact, this))
        setClickListener()
        setRotateAnimation()
    }

    private fun setClickListener() {
        binding.ivHead.setOnClickListener(this)
        binding.ivComments.setOnClickListener(this)
        binding.ivShare.setOnClickListener(this)
        binding.rlLike.setOnClickListener(this)
        binding.ivFocus.setOnClickListener(this)
    }

    //视频运行状态旋转显示动画
    private fun setRotateAnimation() {
        val rotateAnimation = RotateAnimation(0f, 359f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.apply {
            repeatCount = Animation.INFINITE    //无限循环
            duration = 8000     //周期8s
            interpolator = LinearInterpolator() //Interpolator负责控制动画变化的速率，使得基本的动画效果能够以匀速、加速、减速、抛物线速率等各种速率变化。
        }
        binding.rlRecord.startAnimation(rotateAnimation)
    }

    /**
     * 自定义视图数据载入器
     */
    fun setVideoInfoData(videoBean: VideoBean) {
        this.videoBean = videoBean

        binding.ivHead.setImageResource(videoBean.userBean.head)
        binding.tvUser.text = "@"+ videoBean.userBean.nickName

        binding.tcvContent.setText(videoBean.content)
        binding.mtvSubcontent.text = videoBean.userBean.sign
        binding.tcvContent.setOnTextClickListener(object: TextClickListener{//文本关键字段(@、#、url)的点击跳转事件
            override fun onTextClick(matchMode: MatchMode, matchedText: String) {
                when(matchMode) {//点击跳转并传递数据
                    MatchMode.Mode_PERSON -> videoInfoListener.onTextClick(matchMode, matchedText.substring(1))
                    MatchMode.MODE_TOPIC -> videoInfoListener.onTextClick(matchMode, matchedText.substring(1))
                    MatchMode.MODE_URL -> videoInfoListener.onTextClick(matchMode, matchedText.substring(1))
                }
            }
        })

        binding.ivHeadAnim.setImageResource(videoBean.userBean.head)
        binding.tvLikescount.text = NumberUtils.numberFilter(videoBean.likeCount)
        binding.tvCommentscount.text = NumberUtils.numberFilter(videoBean.commentCount)
        binding.tvSharescount.text = NumberUtils.numberFilter(videoBean.shareCount)
        binding.lottieAnim.setAnimation("like.json")    //点赞动画设置

        //关注小按钮显隐设置
        if (videoBean.isFocused) {
            binding.ivFocus.visibility = View.GONE
        } else {
            binding.ivFocus.visibility = View.VISIBLE
        }

        //点赞按钮颜色设置
        if (videoBean.isLiked) {
            binding.ivLike.setTextColor(ContextCompat.getColor(context, R.color.color_like))
        }else {
            binding.ivLike.setTextColor(ContextCompat.getColor(context, R.color.white))
        }

    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.iv_head -> videoInfoListener.onHeadClick()
            R.id.rl_like -> {
                videoInfoListener.onLikeClick()
                updateLikeView()
            }
            R.id.iv_comments -> videoInfoListener.onCommentClick()
            R.id.iv_share -> videoInfoListener.onShareClick()
            R.id.iv_focus -> {
                videoInfoListener.onFocusClick()
                updateFocusView()
            }
        }
    }

    private fun updateFocusView() {
        binding.ivFocus.visibility = View.GONE
    }

    fun updateLikeView() {

        if (!videoBean.isLiked) {//点赞视图切换
            binding.lottieAnim.visibility = View.VISIBLE
            binding.lottieAnim.playAnimation()
            binding.ivLike.setTextColor(ContextCompat.getColor(context, R.color.color_like))
        }else {
            binding.lottieAnim.visibility = View.GONE
            binding.ivLike.setTextColor(ContextCompat.getColor(context, R.color.white))
        }

        videoBean.isLiked = !videoBean.isLiked  //数据修改

    }

    fun setOnVideoInfoClickListener(listener: OnVideoInfoClickListener){
        this.videoInfoListener = listener
    }

    interface OnVideoInfoClickListener {
        fun onHeadClick()
        fun onLikeClick()
        fun onCommentClick()
        fun onShareClick()
        fun onFocusClick()
        fun onTextClick(matchMode: MatchMode, matchedText: String)
    }
}



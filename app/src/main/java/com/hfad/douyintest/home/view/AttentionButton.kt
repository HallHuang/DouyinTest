package com.hfad.douyintest.home.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.hfad.douyintest.R

class AttentionButton(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    var ivCircle: ImageView
    var tvBtn: TextView
    var isClicked:Boolean = true

    init {
        LayoutInflater.from(context).inflate(R.layout.ll_attemtion_button, this)
        tvBtn = findViewById(R.id.tv_title)
        ivCircle = findViewById(R.id.iv_circle)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.AttentionButton)

        val color = typedArray.getColor(R.styleable.AttentionButton_text_color_btn, Color.RED)
        val imgId = typedArray.getResourceId(R.styleable.AttentionButton_icon_img, R.mipmap.head4)

        tvBtn.text = "+关注"
        tvBtn.setTextColor(color)

        ivCircle.setImageResource(imgId)

        this.setOnClickListener {
            tvBtn.text = if(isClicked) "私信" else "+关注"
            isClicked = !isClicked
        }

        typedArray.recycle()
    }

    fun setImage(@DrawableRes imageRes: Int) {
        ivCircle.setImageResource(imageRes)
    }

}
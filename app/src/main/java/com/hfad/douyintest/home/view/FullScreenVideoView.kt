package com.hfad.douyintest.home.view

import android.content.Context
import android.util.AttributeSet
import android.widget.VideoView

class FullScreenVideoView(context: Context, attributeSet: AttributeSet?)
    : VideoView(context, attributeSet) {

    constructor(context: Context): this(context, null)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //EXACTLY: MATCH_PARENT
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec))
    }
}
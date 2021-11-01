package com.hfad.douyintest.home.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class MarqueeTextView(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int): AppCompatTextView(context, attributeSet, defStyleAttr) {
    constructor(context: Context, attributeSet: AttributeSet?): this(context, attributeSet, 0)
    constructor(context: Context): this(context, null)

    override fun isFocused(): Boolean = true
}
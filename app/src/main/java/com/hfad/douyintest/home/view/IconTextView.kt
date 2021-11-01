package com.hfad.douyintest.home.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class IconTextView(context: Context, attributeSet: AttributeSet?): AppCompatTextView(context, attributeSet) {
    constructor(context: Context): this(context, null)

    init {
        typeface = Typeface.createFromAsset(context.assets, "iconfont.ttf")
    }
}
package com.hfad.douyintest.home.view

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.hfad.douyintest.R
import java.util.*
import java.util.regex.Pattern


class TextClickView(context: Context, attrs: AttributeSet?): AppCompatTextView(context, attrs) {

    private var listener: TextClickListener ?= null

    constructor(context: Context): this(context, null)

    override fun setText(text: CharSequence?, type: BufferType?) {

        if (TextUtils.isEmpty(text)) {
            super.setText(text, type)
            return
        }
        setTextColor(ContextCompat.getColor(context, R.color.white))    //字体颜色为白色， context继承父类
        val styledSpanStr = styledSpanStr(text)
        movementMethod = LinkMovementMethod.getInstance()
        super.setText(styledSpanStr, type)
    }

    private fun styledSpanStr(text: CharSequence?):SpannableString {
        val spannableString = SpannableString(text)
        val matchInfoList = matchInfoList(text)
        for (info in matchInfoList) {

            if (info.matchMode == MatchMode.Mode_PERSON || info.matchMode == MatchMode.MODE_TOPIC) {
                spannableString.setSpan(object : ClickableSpan() {

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = ContextCompat.getColor(context, R.color.white)   //下划线白色
                        ds.typeface = Typeface.DEFAULT_BOLD
                        ds.bgColor = ContextCompat.getColor(context, android.R.color.transparent)
                        ds.isUnderlineText = false    //不设置下划线
                    }

                    override fun onClick(p0: View) {
                        listener?.onTextClick(info.matchMode, info.matchedText)
                    }

                }, info.start, info.end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else {//URL
                spannableString.setSpan(object : ClickableSpan() {

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = ContextCompat.getColor(context, R.color.text_gray)   //下划线白色
                        ds.typeface = Typeface.DEFAULT_BOLD
                        ds.bgColor = ContextCompat.getColor(context, android.R.color.transparent)
                        ds.isUnderlineText = true    //设置下划线

                    }

                    override fun onClick(p0: View) {
                        listener?.onTextClick(info.matchMode, info.matchedText)
                    }

                }, info.start, info.end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        return spannableString
    }

    private fun matchInfoList(text: CharSequence?): LinkedList<MatchInfoItem> {
        var matchList = LinkedList<MatchInfoItem>()
        val matchModeList = listOf(MatchMode.Mode_PERSON, MatchMode.MODE_TOPIC, MatchMode.MODE_URL) //模式项列表
        for (mode in matchModeList){
            val regex = getRegexByMode(mode)
            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(text)
            while (matcher.find()) {
                matchList.add(MatchInfoItem(matcher.start(), matcher.end(), matcher.group(), mode)) //匹配信息集合
            }
        }
        return matchList
    }

    private fun getRegexByMode(mode: MatchMode): String =
        when(mode){
            MatchMode.Mode_PERSON -> "@.{1,15}?\\s"
            MatchMode.MODE_TOPIC -> "#.{1,15}?\\s"
            MatchMode.MODE_URL -> "(http|https|ftp|svn)://([a-zA-Z0-9]+[/?.?])" +
                    "+[a-zA-Z0-9]*\\??([a-zA-Z0-9]*=[a-zA-Z0-9]*&?)*"
        }

    public fun setOnTextClickListener(listener: TextClickListener){
        if (listener != null) {
            this.listener = listener
        }
    }

}

data class MatchInfoItem(val start: Int, val end: Int, val matchedText: String, val matchMode: MatchMode)


enum class MatchMode {
    Mode_PERSON,
    MODE_TOPIC,
    MODE_URL
}

interface TextClickListener{
    fun onTextClick(matchMode: MatchMode, matchedText: String)
}

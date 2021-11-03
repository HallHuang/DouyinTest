package com.hfad.douyintest.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.hfad.douyintest.MainActivityKt
import com.hfad.douyintest.activities.SearchActivity
import com.hfad.douyintest.activities.VideoPlayActivity

object ActivityUtils {
    const val CLICKTEXT = "clicktext"

    fun switchToVideoPlayActivity(context: Context, clazz: Class<VideoPlayActivity>, transferStr: String) {
        val intent = Intent(context, clazz)
        intent.putExtra(CLICKTEXT, transferStr)
        context.startActivity(intent)
    }

    fun switchToSearchActivity(context: Context, clazz: Class<SearchActivity>, transferStr: String) {
        val intent = Intent(context, clazz)
        intent.putExtra(CLICKTEXT, transferStr)
        context.startActivity(intent)
    }

    fun switchToMainActivity(context: Context, clazz: Class<MainActivityKt>, transferStr: String) {
        val intent = Intent(context, clazz)
        intent.putExtra(CLICKTEXT, transferStr)
        context.startActivity(intent)
    }

    fun switchToBrowser(context: Context?, transferStr: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(transferStr))
        context?.startActivity(browserIntent)
    }

}
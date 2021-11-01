package com.hfad.douyintest.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.hfad.douyintest.activities.SearchActivity

object ActivityUtils {
    const val CLICKTEXT = "clicktext"

    fun switchToSearchActivity(context: Context, clazz: Class<SearchActivity>, transferStr: String) {
        val intent = Intent(context, clazz)
        intent.putExtra(CLICKTEXT, transferStr)
        context.startActivity(intent)
    }

    fun switchToBrowser(context: Context?, transferStr: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(transferStr))
        context?.startActivity(browserIntent)
    }

}
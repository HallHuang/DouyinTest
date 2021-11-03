package com.hfad.douyintest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.hfad.douyintest.MainActivityKt
import com.hfad.douyintest.R
import com.hfad.douyintest.utils.ActivityUtils
import java.util.*

/**
 * 延迟1s后进入MainActivityKt
 */
class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        Handler(Looper.getMainLooper()).postDelayed({
            //自定义方法，活动跳转
            ActivityUtils.switchToMainActivity(this, MainActivityKt::class.java, "")
        }, 1000)
    }
}
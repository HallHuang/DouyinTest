package com.hfad.douyintest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hfad.douyintest.R

class VideoPlayActivity : AppCompatActivity() {
    companion object{
        var posPlay: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
    }
}
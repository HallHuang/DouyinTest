package com.hfad.douyintest.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hfad.douyintest.R
import com.hfad.douyintest.databinding.ActivityVideoPlayBinding
import com.hfad.douyintest.home.fragments.RecommandFragment

/**
 * 点击视频列表后跳转到的视频播放界面，和RecommandFragment类似，不做具体实现，只显示播放位置
 */
class VideoPlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoPlayBinding
    companion object{
        var posPlay: Int = 0
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvVideoPlay.text = "VideoPosition: $posPlay"
        binding.ivBackToList.setOnClickListener {
            finish()
        }
    }
}
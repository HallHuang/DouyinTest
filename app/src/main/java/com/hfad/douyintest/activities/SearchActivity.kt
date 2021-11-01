package com.hfad.douyintest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.hfad.douyintest.R
import com.hfad.douyintest.databinding.ActivitySearchBinding
import com.hfad.douyintest.utils.ActivityUtils.CLICKTEXT

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val s = intent.getStringExtra(CLICKTEXT)
        binding.tvSearch.text = "搜索关键词是："+s
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}
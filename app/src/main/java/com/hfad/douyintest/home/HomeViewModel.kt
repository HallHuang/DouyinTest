package com.hfad.douyintest.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    val isBackToHome by lazy {
        MutableLiveData<Boolean>().apply {
            this.value = false
        }
    }

    val curPage by lazy {
        MutableLiveData<Int>().apply {
            this.value = 0
        }
    }
}
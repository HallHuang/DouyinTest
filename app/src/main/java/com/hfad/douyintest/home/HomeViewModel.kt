package com.hfad.douyintest.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    val isBackToHome by lazy {
        MutableLiveData<Boolean>().apply {
            this.value = false
        }
    }



}
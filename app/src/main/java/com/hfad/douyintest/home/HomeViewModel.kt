package com.hfad.douyintest.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hfad.douyintest.home.beans.DataCreate
import com.hfad.douyintest.home.beans.VideoBean

class HomeViewModel: ViewModel() {

    val isBackToHome by lazy {
        MutableLiveData<Boolean>().apply {
            this.value = true
        }
    }

    val curUserBeanData by lazy {
        MutableLiveData<VideoBean.UserBean>()
    }

    val isVideoFragment by lazy {
        MutableLiveData<Boolean>().apply {
            this.value = true
        }
    }

    val curVideoPos by lazy {
        MutableLiveData<Int>().apply {
            this.value = 0
        }
    }
}
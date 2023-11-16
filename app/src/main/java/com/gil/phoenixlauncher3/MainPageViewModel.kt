package com.gil.phoenixlauncher3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainPageViewModel: ViewModel() {

    private val mAppList= MutableLiveData<List<AppData>>()
    private val mSecondAppList= MutableLiveData<List<AppData>>()
    val appList: LiveData<List<AppData>>
        get() {
            return mAppList
        }
    val seconds: LiveData<List<AppData>>
        get() {
            return mSecondAppList
        }

    init {
        this.mAppList.value = mutableListOf()
    }

    fun setMainApplication(list: List<AppData>){
        this.mAppList.value = list
    }
    fun setApplications(list: List<AppData>){
        this.mSecondAppList.value = list
    }
}
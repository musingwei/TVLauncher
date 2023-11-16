package com.gil.phoenixlauncher3

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.gil.phoenixlauncher3.databinding.ActivityMainBinding
import com.gil.phoenixlauncher3.utils.SystemApp

class MainActivity : AppCompatActivity(){

    companion object{
        private const val TAG = "Main"
    }
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainPageViewModel: MainPageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainPageViewModel = ViewModelProvider(this)[MainPageViewModel::class.java]
        mainBinding.viewmodel = mainPageViewModel
        setupElements()
        setupSettingRow()

    }

    private fun setupSettingRow() {
        
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun setupElements() {
        val installedApplications = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val mainList = mutableListOf<AppData>()
        val anyList = mutableListOf<AppData>()
        val appIterator = installedApplications.iterator()
        while (appIterator.hasNext()){
            val applicationInfo = appIterator.next()
            val appData = AppData(packageManager, applicationInfo)
            if (SystemApp.SYSTEM_APP_LIST.contains(appData.packageName)){
                mainList.add(appData)
            }
            else{
                if (appData.banner != null){
                    anyList.add(appData)
                }
            }
        }
        mainPageViewModel.setMainApplication(mainList)
        mainPageViewModel.setApplications(anyList)
    }


}
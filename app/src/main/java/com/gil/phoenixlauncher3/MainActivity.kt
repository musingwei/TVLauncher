package com.gil.phoenixlauncher3

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.leanback.widget.HorizontalGridView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.gil.phoenixlauncher3.SettingsAdapter.Companion.ITEM_INSTALLED_APP
import com.gil.phoenixlauncher3.SettingsAdapter.Companion.ITEM_DISPLAY_SETTINGS
import com.gil.phoenixlauncher3.SettingsAdapter.Companion.ITEM_NETWORK
import com.gil.phoenixlauncher3.SettingsAdapter.Companion.ITEM_SETTINGS
import com.gil.phoenixlauncher3.databinding.ActivityMainBinding
import com.gil.phoenixlauncher3.utils.SystemApp

class MainActivity : AppCompatActivity(){

    companion object{
        private const val TAG = "Main"
    }
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainPageViewModel: MainPageViewModel
    private var lastKeyDownTime = -1L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainPageViewModel = ViewModelProvider(this)[MainPageViewModel::class.java]
        mainBinding.viewmodel = mainPageViewModel
        setupElements()
        setupSettingRow()

    }


    private fun setupSettingRow() {
        val defaultMargin = resources.getDimensionPixelSize(R.dimen.settings_item_margin)
        mainBinding.settingsFrame.adapter = SettingsAdapter(object : SettingsAdapter.OnItemClickListener{
            override fun onClick(adapterPosition: Int) {
                when (adapterPosition) {
                    ITEM_SETTINGS -> {
                        startActivity(Intent(Settings.ACTION_SETTINGS))
                    }
                    ITEM_NETWORK -> {
                        startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                    }
                    ITEM_INSTALLED_APP -> {
                    }
                }
            }
        })
        mainBinding.settingsFrame.layoutManager = object : LinearLayoutManager(this, RecyclerView.HORIZONTAL, false) {
            override fun onInterceptFocusSearch(focused: View, direction: Int): View? {
                if (direction == View.FOCUS_RIGHT) {
                    val pos = getPosition(focused)
                    if (pos == itemCount - 1)
                        return focused
                }
                return super.onInterceptFocusSearch(focused, direction)
            }
        }
        mainBinding.settingsFrame.addItemDecoration(object : RecyclerView.ItemDecoration(){
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)

                outRect.right += defaultMargin*2
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.left += resources.getDimensionPixelSize(R.dimen.list_view_margin)
                }
            }
        })

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
                    if (appData.packageName != "com.android.settings"){
                        anyList.add(appData)
                    }
                }
            }
        }

        mainPageViewModel.setMainApplication(mainList)
        mainPageViewModel.setApplications(anyList)
    }


}
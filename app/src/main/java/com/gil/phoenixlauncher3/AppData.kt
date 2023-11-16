package com.gil.phoenixlauncher3

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable

data class AppData(val label: String? = null,
               val packageName : String? = null,
               val banner: Drawable? = null,
               val icon: Drawable? = null) {


    constructor(packageManager: PackageManager,
                applicationInfo: ApplicationInfo):
            this(
                label= applicationInfo.loadLabel(packageManager).toString(),
                packageName = applicationInfo.packageName,
                banner = applicationInfo.loadBanner(packageManager),
                icon = applicationInfo.loadIcon(packageManager)
            )

}
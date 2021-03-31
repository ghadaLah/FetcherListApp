package datadome.fr.fetcher

import android.app.Activity
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.graphics.Point
import android.os.Build
import android.util.Log
import android.view.Display

class FetcherInfos(val activity: Activity) {
    private val info: PackageInfo

    init {
        val manager = activity.packageManager
        info = manager.getPackageInfo(activity.packageName, PackageManager.GET_ACTIVITIES)
        getAppInfos()
        getScreenSize()
    }

    fun getAppInfos(): AppInfos {
        val versionCode = info.versionCode
        val versionName = info.versionName
        val releaseVersion = Build.VERSION.RELEASE
        val sdkVersion = Build.VERSION.SDK_INT
        val permissions = info.permissions
        val targetSdkVersion = info.applicationInfo.targetSdkVersion
        val sourceDir = info.applicationInfo.sourceDir
        val actionBarTitle = activity.actionBar?.title
        val isWindowActive = activity.window.isActive
        return AppInfos(
            versionCode,
            versionName,
            releaseVersion,
            sdkVersion,
            permissions,
            targetSdkVersion,
            sourceDir,
            actionBarTitle,
            isWindowActive
        )
    }

    data class AppInfos(
        val versionCode: Int,
        val versionName: String,
        val releaseVersion: String,
        val sdkVersion: Int,
        val permissions: Array<PermissionInfo>?,
        val targetSdkVersion: Int,
        val sourceDir: String?,
        val actionBarTitle: CharSequence?,
        val isWindowActive: Boolean
    )

    fun getScreenSize(): Pair<Int, Int> {
        val display: Display = activity.getWindowManager().getDefaultDisplay()
        val size = Point()
        display.getSize(size)
        val width: Int = size.x
        val height: Int = size.y
        return Pair(width, height)
    }

}


package top.bear3.gank_kotlin.app

import android.app.Application
import timber.log.Timber

/**
 * Description:
 * Author: TT
 * From: 2019/8/15
 * Last Version: 1.0.0
 * Last Change Time: 2019/8/15
 * ----------- History ---------
 * *-*
 * version:
 * description:
 * time: 2019/8/15
 * *-*
 */
class GankApp : Application() {
    companion object {
        var appContext : GankApp? = null
        fun getContext() : GankApp {
            return appContext!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this

        Timber.plant(Timber.DebugTree())
    }
}
package top.bear3.gank_kotlin.ui.main

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import top.bear3.gank_kotlin.http.HttpError
import top.bear3.gank_kotlin.http.HttpManager
import top.bear3.gank_kotlin.http.HttpObserver
import top.bear3.gank_kotlin.http.ResponseModel
import top.bear3.gank_kotlin.ui.common.activity.BaseActivityPresenter
import top.bear3.gank_kotlin.ui.common.activity.BaseActivityView

/**
 * Description:
 * Author: TT
 * From: 2019/8/14
 * Last Version: 1.0.0
 * Last Change Time: 2019/8/14
 * ----------- History ---------
 * *-*
 * version:
 * description:
 * time: 2019/8/14
 * *-*
 */
interface MainView : BaseActivityView {
    fun requestTodaySuccess(categories : Map<String, List<TodayDetail>>)
}

class MainPresenter : BaseActivityPresenter<MainView>() {
    fun requestToday() {
        HttpManager.gankApi.getToday()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: HttpObserver<Map<String, List<TodayDetail>>>() {
                override fun onSuccess(data: ResponseModel<Map<String, List<TodayDetail>>>) {
                    data.results?.let {
                        getView()?.requestTodaySuccess(it)
                    }
                }

                override fun onFailed(error: HttpError) {

                }
            })
    }
}
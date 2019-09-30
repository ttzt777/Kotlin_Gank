package com.bear3.gank_kotlin.mvp

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
interface MvpPresenter<V : MvpView> {
    fun attachView(view : V)

    fun detachView()
}
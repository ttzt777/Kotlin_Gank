package com.bear3.gank_kotlin.mvp

import java.lang.ref.WeakReference

/**
 * Description:
 * Author: TT
 * From: 2019/8/13
 * Last Version: 1.0.0
 * Last Change Time: 2019/8/13
 * ----------- History ---------
 * *-*
 * version:
 * description:
 * time: 2019/8/13
 * *-*
 */
abstract class MvpBasePresenter<V : MvpView> : MvpPresenter<V> {
    lateinit var weakRef : WeakReference<V>

    override fun attachView(view: V) {
        weakRef = WeakReference(view)
    }

    override fun detachView() {
        weakRef.clear()
    }

    fun getView() : V? {
        return weakRef.get()
    }
}
package com.bear3.gank_kotlin.mvp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

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
abstract class MvpFragment<V : MvpView, P : MvpBasePresenter<V>> : Fragment(), MvpView {
    protected lateinit var presenter: P

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = createPresenter()
        presenter.attachView(this as V)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    abstract fun createPresenter() : P
}
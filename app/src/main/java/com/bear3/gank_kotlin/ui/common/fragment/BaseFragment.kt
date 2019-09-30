package com.bear3.gank_kotlin.ui.common.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bear3.gank_kotlin.mvp.MvpFragment
import com.bear3.gank_kotlin.ui.common.activity.BaseFragmentPresenter
import com.bear3.gank_kotlin.ui.common.activity.BaseFragmentView

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
abstract class BaseFragment<V : BaseFragmentView, P : BaseFragmentPresenter<V>> : MvpFragment<V, P>(),
    BaseFragmentView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(savedInstanceState)
        loadData()
    }

    abstract fun getLayoutId() : Int
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun loadData()
}
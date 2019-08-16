package top.bear3.gank_kotlin.ui.common.activity

import android.os.Bundle
import top.bear3.gank_kotlin.mvp.MvpActivity
import top.bear3.gank_kotlin.mvp.MvpView

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
abstract class BaseActivity<V : BaseActivityView, P : BaseActivityPresenter<V>> : MvpActivity<V, P>(),
    BaseActivityView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        initView(savedInstanceState)
        loadData()
    }

    abstract fun getLayoutId() : Int
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun loadData()
}
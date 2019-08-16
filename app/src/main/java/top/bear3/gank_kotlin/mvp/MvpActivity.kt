package top.bear3.gank_kotlin.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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
abstract class MvpActivity<V : MvpView, P : MvpPresenter<V>> : AppCompatActivity(), MvpView {
    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = createPresenter()
        presenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    abstract fun createPresenter() : P
}
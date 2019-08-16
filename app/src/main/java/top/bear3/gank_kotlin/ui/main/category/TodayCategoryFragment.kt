package top.bear3.gank_kotlin.ui.main.category

import android.os.Bundle
import top.bear3.gank_kotlin.R
import top.bear3.gank_kotlin.ui.common.fragment.BaseFragment
import top.bear3.gank_kotlin.ui.main.TodayDetail
import java.util.ArrayList

private const val ARG_DATA = "args_data"

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
class TodayCategoryFragment : BaseFragment<TodayCategoryView, TodayCategoryPresenter>(), TodayCategoryView {
    private var datas: List<TodayDetail>? = null

    companion object {
        @JvmStatic
        fun newInstance(datas: ArrayList<TodayDetail>?) : TodayCategoryFragment {
            return TodayCategoryFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_DATA, datas)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            datas = it.getSerializable(ARG_DATA) as ArrayList<TodayDetail>
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun loadData() {

    }

    override fun createPresenter(): TodayCategoryPresenter {
        return TodayCategoryPresenter()
    }
}
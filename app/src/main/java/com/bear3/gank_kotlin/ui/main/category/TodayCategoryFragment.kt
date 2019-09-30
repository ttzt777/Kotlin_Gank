package com.bear3.gank_kotlin.ui.main.category

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bear3.gank_kotlin.R
import com.bear3.gank_kotlin.ui.common.fragment.BaseFragment
import com.bear3.gank_kotlin.ui.main.TodayDetail
import kotlinx.android.synthetic.main.fragment_tody_category.*
import java.util.*

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
    private lateinit var adapter: TodayCategoryAdapter

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
        return R.layout.fragment_tody_category
    }

    override fun initView(savedInstanceState: Bundle?) {
        adapter = TodayCategoryAdapter(context!!)
        with(list) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@TodayCategoryFragment.adapter
        }
    }

    override fun loadData() {
        adapter.updateDataList(datas)
    }

    override fun createPresenter(): TodayCategoryPresenter {
        return TodayCategoryPresenter()
    }
}
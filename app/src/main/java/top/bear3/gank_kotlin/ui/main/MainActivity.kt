package top.bear3.gank_kotlin.ui.main

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import top.bear3.gank_kotlin.R
import top.bear3.gank_kotlin.ui.common.activity.BaseActivity
import top.bear3.gank_kotlin.ui.common.adapter.pager.ViewPagerAdapter
import top.bear3.gank_kotlin.ui.main.category.TodayCategoryFragment

class MainActivity : BaseActivity<MainView, MainPresenter>(), MainView {

    lateinit var adapter: ViewPagerAdapter<TodayCategoryFragment>

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        adapter = ViewPagerAdapter(supportFragmentManager)
        view_pager.adapter = adapter
        tabs.setupWithViewPager(view_pager)
    }

    override fun loadData() {
        presenter.requestToday()
    }

    override fun createPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun requestTodaySuccess(categories: Map<String, List<TodayDetail>>) {
        var titles = ArrayList<String>()
        var fragments = ArrayList<TodayCategoryFragment>()

        categories.forEach {
            titles.add(it.key)
            fragments.add(TodayCategoryFragment.newInstance(it.value as java.util.ArrayList<TodayDetail>))
        }
        adapter.setFragments(titles, fragments)
    }
}
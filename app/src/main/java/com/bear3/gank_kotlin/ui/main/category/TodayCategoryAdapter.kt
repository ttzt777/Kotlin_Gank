package com.bear3.gank_kotlin.ui.main.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bear3.gank_kotlin.R
import com.bear3.gank_kotlin.ui.main.TodayDetail
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tody_category.view.*

/**
 * Description:
 * Author: TT
 * From: 2019/8/16
 * Last Version: 1.0.0
 * Last Change Time: 2019/8/16
 * ----------- History ---------
 * *-*
 * version:
 * description:
 * time: 2019/8/16
 * *-*
 */
class TodayCategoryAdapter(var context: Context) : RecyclerView.Adapter<TodayItemViewHolder>() {
    private var dataList: MutableList<TodayDetail> = ArrayList()

    fun updateDataList(dataList: List<TodayDetail>?) {
        this.dataList.clear()

        dataList?.let {
            this.dataList.addAll(dataList)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_tody_category, parent, false)
        return TodayItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodayItemViewHolder, position: Int) {
        val data = dataList[position]

        data.let {
            holder.itemView.title.text = it.desc
            holder.itemView.source.text = it.who
            holder.itemView.time.text = it.createdAt

            with(holder.itemView.images) {
                if ("福利" == it.type) {
                    updateData(it.url)
                } else {
                    visibility = if(it.images.isNullOrEmpty()) View.GONE else View.VISIBLE
                    updateData(it.images)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

class TodayItemViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer
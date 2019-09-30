package com.bear3.gank_kotlin.ui.common.weight

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bear3.gank_kotlin.R
import com.bear3.gank_kotlin.ui.common.utils.loadImageUrl
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_hor_multi_pic.view.*

/**
 * Description:
 * Author: TT
 * From: 2019/9/11
 */
internal class HorMultiPicView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private var adapter: HorMultiPicAdapter = HorMultiPicAdapter(context)

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        setAdapter(adapter)
    }

    fun updateData(imageUrl: String?) {
        val dataList = ArrayList<String>()

        imageUrl?.let {
            dataList.add(imageUrl)
        }

        updateData(dataList)
    }

    fun updateData(imageUrlArray: List<String>?) {
        adapter.updateDataList(imageUrlArray)
    }
}

private class HorMultiPicAdapter(var context: Context) : RecyclerView.Adapter<ViewHolder>() {
    val dataList: MutableList<String> = ArrayList()

    fun updateDataList(dataList: List<String>?) {
        this.dataList.clear()

        dataList?.let {
            this.dataList.addAll(it)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ViewHolder(inflate.inflate(R.layout.item_hor_multi_pic, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = dataList[position]
        loadImageUrl(context, imageUrl, holder.containerView.image)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

private class ViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer
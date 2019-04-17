package phuchh.sunasterisk.projectmoviedb.base

import android.support.v7.widget.RecyclerView
import java.util.ArrayList

abstract class BaseRecyclerViewAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {
    protected var mData: MutableList<T> = ArrayList()
    protected lateinit var mItemListener: ItemOnClickListener<T>

    fun setItemListener(listener: ItemOnClickListener<T>) {
        mItemListener = listener
    }

    fun setData(data: List<T>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(data: List<T>) {
        val positionStart = data.size
        val itemCount = positionStart - 1
        mData.addAll(data)
        notifyItemRangeChanged(positionStart, itemCount)
    }

    fun addData(data: T) {
        val position = mData.size - 1
        mData.add(data)
        notifyItemInserted(position)
    }

    fun getData(): List<T> {
        return mData
    }

    fun addItem(t: T) {
        val position = mData.size - 1
        mData.add(t)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        mData.removeAt(position)
        notifyItemRemoved(position)
    }


    interface ItemOnClickListener<T> {
        fun onItemClick(t: T, position: Int)
    }
}
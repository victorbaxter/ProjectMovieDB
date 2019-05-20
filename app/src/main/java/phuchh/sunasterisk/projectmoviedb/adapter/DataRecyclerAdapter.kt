package phuchh.sunasterisk.projectmoviedb.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import phuchh.sunasterisk.projectmoviedb.BR

class DataRecyclerAdapter<T>(private val callback: AdapterCallback, private val layoutRes:Int) :
    RecyclerView.Adapter<DataRecyclerAdapter.ViewHolder>() {

    private var data: MutableList<T>

    init {
        data = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutRes,
            parent,
            false
        )
        binding.setVariable(BR.callback, callback)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.setVariable(BR.data, data[position])
    }

    fun setData(data: List<T>) {
        this.data = data as MutableList<T>
        notifyDataSetChanged()
    }

    fun clearAll(){
        data.clear()
    }

    fun addData(data: List<T>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(
        var binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
package phuchh.sunasterisk.projectmoviedb.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.data.model.Genre

class GenreRecyclerAdapter(val callback: AdapterCallback) : RecyclerView.Adapter<GenreRecyclerAdapter.ViewHolder>() {
    private var genres: List<Genre>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: phuchh.sunasterisk.projectmoviedb.databinding.ItemGenreBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_genre,
            parent,
            false
        )
        binding.callback = callback
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = if (genres == null) 0 else genres!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.genre = genres!![position]
    }

    fun setGenres(genres: List<Genre>) {
        this.genres = genres
        notifyDataSetChanged()
    }

    class ViewHolder(
        var binding: phuchh.sunasterisk.projectmoviedb.databinding.ItemGenreBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
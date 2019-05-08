package phuchh.sunasterisk.projectmoviedb.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.data.model.Movie

class MovieRecyclerAdapter(private val callback: AdapterCallback) : RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>() {
    private var mMovies : List<Movie>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: phuchh.sunasterisk.projectmoviedb.databinding.ItemMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie,
            parent,
            false
        )
        binding.callback = callback
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (mMovies ==null) 0 else mMovies!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.movie = mMovies!![position]
    }

    fun setMovies(movies: List<Movie>) {
        mMovies = movies
        notifyDataSetChanged()
    }

    class ViewHolder(
        var binding: phuchh.sunasterisk.projectmoviedb.databinding.ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
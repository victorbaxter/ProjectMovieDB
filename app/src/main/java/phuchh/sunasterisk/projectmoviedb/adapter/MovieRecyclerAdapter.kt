package phuchh.sunasterisk.projectmoviedb.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import phuchh.sunasterisk.projectmoviedb.BR
import phuchh.sunasterisk.projectmoviedb.data.model.Movie

class MovieRecyclerAdapter(private val callback: AdapterCallback) :
    RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>() {
    private var movies: MutableList<Movie> = mutableListOf()
    private var layoutRes: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutRes!!,
            parent,
            false
        )
        binding.setVariable(BR.callback, callback)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.setVariable(BR.movie , movies[position])
    }

    fun setMovies(movies: List<Movie>) {
        this.movies = movies as MutableList<Movie>
        notifyDataSetChanged()
    }

    fun addMovies(movies: List<Movie>){
        this.movies.addAll(movies)
    }

    fun setLayoutRes(layoutRes: Int) {
        this.layoutRes = layoutRes
    }

    class ViewHolder(
        var binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
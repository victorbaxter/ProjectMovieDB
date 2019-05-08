package phuchh.sunasterisk.projectmoviedb.utils

import android.databinding.BindingAdapter
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.MovieRecyclerAdapter
import phuchh.sunasterisk.projectmoviedb.data.model.Movie

object BindingUtils {
    @JvmStatic
    @BindingAdapter("bindMovies")
    fun bindMovies(recycler: RecyclerView, movies: ObservableList<Movie>) {
        val adapter = recycler.adapter as MovieRecyclerAdapter?
        adapter!!.setMovies(movies)
    }

    @JvmStatic
    @BindingAdapter("bindImage")
    fun bindImage(imageView: ImageView, image_path: String) {
        GlideApp.with(imageView)
            .load(StringUtils.getImage(image_path))
            .error(R.drawable.no_image)
            .into(imageView)
    }
}

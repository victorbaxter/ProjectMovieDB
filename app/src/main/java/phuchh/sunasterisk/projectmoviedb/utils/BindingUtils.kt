package phuchh.sunasterisk.projectmoviedb.utils

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.data.model.Movie

object BindingUtils {
    @JvmStatic
    @BindingAdapter("bindImage")
    fun bindImage(imageView: ImageView, image_path: String) {
        GlideApp.with(imageView)
            .load(StringUtils.getImage(image_path))
            .error(R.drawable.no_image)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("bindProductionCompany")
    fun bindProductionCompany(textView: TextView, movie: Movie) {
        if (movie.productionCompanies != null) {
            textView.text = StringUtils.getProductionCompany(movie.productionCompanies)
            textView.isSelected = true
        }
    }


    @JvmStatic
    @BindingAdapter("bindGenres")
    fun bindGenres(textView: TextView, movie: Movie) {
        val genres = movie.genres
        if (genres != null) {
            textView.text = StringUtils.getGenres(genres)
            textView.isSelected = true
        }
    }
}

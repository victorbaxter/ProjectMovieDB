package phuchh.sunasterisk.projectmoviedb.utils

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.data.model.Actor
import phuchh.sunasterisk.projectmoviedb.data.model.Movie

object BindingUtils {
    private const val NOT_YET = "Not Yet"
    @JvmStatic
    @BindingAdapter("bindImage")
    fun bindImage(imageView: ImageView, image_path: String?) {
        GlideApp.with(imageView)
            .load(StringUtils.getImage(image_path))
            .error(R.drawable.no_image)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("bindProductionCompany")
    fun bindProductionCompany(textView: TextView, movie: Movie?) {
        if (movie?.productionCompanies != null) {
            textView.text = StringUtils.getProductionCompany(movie.productionCompanies)
            textView.isSelected = true
        }
    }


    @JvmStatic
    @BindingAdapter("bindGenres")
    fun bindGenres(textView: TextView, movie: Movie?) {
        if (movie == null) return
        val genres = movie.genres
        if (genres != null) {
            textView.text = StringUtils.getGenres(genres)
            textView.isSelected = true
        }
    }

    @JvmStatic
    @BindingAdapter("bindActorPoB")
    fun bindActorPoB(textView: TextView, actor: Actor?) {
        if (actor == null) return
        textView.text = StringUtils.getActorPOB(actor)
    }

    @JvmStatic
    @BindingAdapter("bindDuration")
    fun bindDuration(textView: TextView, movie: Movie?) {
        if (movie == null) return
        textView.text = StringUtils.getDuration(movie)
    }

    @JvmStatic
    @BindingAdapter("bindTextMarquee")
    fun bindTextMarquee(textView: TextView, text: String?) {
        if (text == null) return
        textView.text = text
        textView.isSelected = true
    }

    @JvmStatic
    @BindingAdapter("bindDeadDay")
    fun bindDeadDay(textView: TextView, text: String?) {
        if (text == null) {
            textView.text = NOT_YET
            return
        }
        textView.text = text
    }
}

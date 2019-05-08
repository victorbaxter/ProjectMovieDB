package phuchh.sunasterisk.projectmoviedb.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.data.model.Movie

class SliderAdapter(val context: Context) : PagerAdapter() {
    private var movies: List<Movie>? = null
    override fun getCount(): Int = if (movies == null) 0 else movies!!.size

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding: phuchh.sunasterisk.projectmoviedb.databinding.ItemSliderBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_slider,
            container,
            false
        )
        val view = binding.root
        binding.movie = movies!![position]
        val viewPager: ViewPager = container as ViewPager
        viewPager.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        val viewPager = container as ViewPager
        val view = obj as View
        viewPager.removeView(view)
    }

    fun setMovies(movies: List<Movie>){
        this.movies = movies
    }
}
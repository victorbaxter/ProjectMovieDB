package phuchh.sunasterisk.projectmoviedb.ui.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.DetailsViewPagerAdapter
import phuchh.sunasterisk.projectmoviedb.base.BaseActivity
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.ApiResponse
import phuchh.sunasterisk.projectmoviedb.databinding.ActivityDetailsBinding
import phuchh.sunasterisk.projectmoviedb.ui.cast.CastFragment
import phuchh.sunasterisk.projectmoviedb.ui.movie.MovieDetailsFragment
import phuchh.sunasterisk.projectmoviedb.ui.producer.ProducerFragment
import phuchh.sunasterisk.projectmoviedb.ui.trailer.YouTubeFragment
import phuchh.sunasterisk.projectmoviedb.utils.ViewModelFactory


class DetailsActivity : BaseActivity<ActivityDetailsBinding, DetailsViewModel>() {
    companion object {
        private const val TITLE_DETAILS = "Details"
        private const val TITLE_CAST = "Cast"
        private const val TITLE_PRODUCERS = "Producers"
        private const val EXTRA_MOVIE_ID = "phuchh.sunasterisk.projectmoviedb.extras.EXTRA_MOVIE_ID"
        private const val ADDED_MSG = "Added to favorite"
        private const val REMOVED_MSG = "Removed from favorite"

        fun getIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, id)
            return intent
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_details
    override lateinit var viewModel: DetailsViewModel
    private lateinit var viewBinding: ActivityDetailsBinding
    private lateinit var youTubeFragment: YouTubeFragment
    private lateinit var movie: Movie

    override fun onDestroy() {
        super.onDestroy()
        viewModel.dispose()
    }

    override fun initComponent(viewBinding: ActivityDetailsBinding, savedInstanceState: Bundle?) {
        this.viewBinding = viewBinding
        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        initViewModel()
        observeViewModel(movieId)
        initTabs(viewBinding, movieId)
        viewBinding.btnDetailsBack.setOnClickListener { onBackPressed() }
        youTubeFragment = supportFragmentManager.findFragmentById(R.id.fragmentYoutube) as YouTubeFragment
        viewBinding.btnDetailsLike.setOnClickListener {
            viewModel.addFavorite(movie)
            showToast(ADDED_MSG)
        }
        viewBinding.btnDetailsDislike.setOnClickListener {
            viewModel.deleteFavorite(movie)
            showToast(REMOVED_MSG)

        }
        viewModel.isFavorite(movieId)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders
            .of(this, ViewModelFactory.getInstance(application))
            .get(DetailsViewModel::class.java)
    }

    private fun initTabs(binding: ActivityDetailsBinding, movieId: Int) {
        val adapter = DetailsViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MovieDetailsFragment.newInstance(movieId), TITLE_DETAILS)
        adapter.addFragment(CastFragment.newInstance(movieId), TITLE_CAST)
        adapter.addFragment(ProducerFragment.newInstance(movieId), TITLE_PRODUCERS)
        val viewPager = binding.pagerDetails
        viewPager.adapter = adapter
        binding.tabDetails.setupWithViewPager(viewPager)
    }

    private fun observeViewModel(movieId: Int) {
        viewModel.getMovieDetails(movieId).observe(this,
            Observer<ApiResponse<Movie>> {
                it?.let {
                    updateUI(it)
                    movie = it.result!!
                }
            })
        viewModel.isFavorite.observe(this, Observer {
            viewBinding.btnDetailsLike.visibility = if (it!!) View.INVISIBLE else View.VISIBLE
            viewBinding.btnDetailsDislike.visibility = if (!it) View.GONE else View.VISIBLE
        })
    }

    private fun updateUI(response: ApiResponse<Movie>?) {
        if (response != null) {
            val error: Throwable? = response.error
            val movie: Movie? = response.result
            if (error != null) {
                showToast(error.message!!)
                return
            }
            viewBinding.movie = movie
            if (movie!!.videoResult!!.videos!!.isNotEmpty()) {
                val trailer = movie.videoResult!!.videos!!.filter { it.type.equals("trailer", true) }
                youTubeFragment.setTrailerKey(trailer[0].key!!)
            }
        }
    }
}

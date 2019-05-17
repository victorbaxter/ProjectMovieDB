package phuchh.sunasterisk.projectmoviedb.ui.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
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

    override fun initComponent(viewBinding: ActivityDetailsBinding, savedInstanceState: Bundle?) {
        this.viewBinding = viewBinding
        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        initViewModel()
        observeViewModel(movieId)
        initTabs(viewBinding, movieId)
        viewBinding.btnDetailsBack.setOnClickListener { onBackPressed() }
        youTubeFragment = supportFragmentManager.findFragmentById(R.id.fragmentYoutube) as YouTubeFragment
    }

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        youTubeFragment.setFullScreen(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
//    }

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
                if (it != null) {
                    updateUI(it)
                }
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
            viewBinding.textMovieTitle.text = movie!!.title
            youTubeFragment.setTrailerKey(movie.videoResult!!.videos!![0].key!!)
        }
    }
}

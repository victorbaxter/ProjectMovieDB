package phuchh.sunasterisk.projectmoviedb.ui.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.DetailsViewPagerAdapter
import phuchh.sunasterisk.projectmoviedb.base.BaseActivity
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository
import phuchh.sunasterisk.projectmoviedb.data.source.local.MovieLocalDataSource
import phuchh.sunasterisk.projectmoviedb.data.source.remote.MovieRemoteDataSource
import phuchh.sunasterisk.projectmoviedb.databinding.ActivityDetailsBinding
import phuchh.sunasterisk.projectmoviedb.ui.movie.MovieDetailsFragment
import phuchh.sunasterisk.projectmoviedb.utils.Constants

private const val TITLE_DETAILS = "Details"
private const val TITLE_CAST = "Cast"
private const val TITLE_PRODUCERS = "Producers"

class DetailsActivity : BaseActivity<ActivityDetailsBinding, DetailsViewModel>() {
    override fun getLayoutRes(): Int = R.layout.activity_details
    override lateinit var viewModel: DetailsViewModel
    private var movieId: Int = 0
    override fun initComponent(viewBinding: ActivityDetailsBinding, savedInstanceState: Bundle?) {
        movieId = intent.extras!![Constants.EXTRA_MOVIE_ID] as Int
        initViewModel()
        initTabs(viewBinding)
        viewBinding.btnDetailsBack.setOnClickListener { onBackPressed() }
    }

    private fun initViewModel() {
        val viewModelFactory = DetailsViewModel.Factory(
            MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance(this),
                MovieLocalDataSource.getInstance()
            )
        )
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)
        observeViewModel()
    }


    private fun initTabs(binding: ActivityDetailsBinding) {
        val adapter = DetailsViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MovieDetailsFragment.newInstance(movieId), TITLE_DETAILS)
        val viewPager = binding.pagerDetails
        viewPager.adapter = adapter
        binding.tabDetails.setupWithViewPager(viewPager)
    }

    private fun observeViewModel() {
        viewModel.getMovieDetails(movieId).observe(this,
            Observer<Movie> { movie ->
                if (movie != null) {

                }
            })
    }
}

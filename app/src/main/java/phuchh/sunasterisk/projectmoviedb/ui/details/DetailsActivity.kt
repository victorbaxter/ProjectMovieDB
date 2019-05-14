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
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository
import phuchh.sunasterisk.projectmoviedb.data.source.local.MovieLocalDataSource
import phuchh.sunasterisk.projectmoviedb.data.source.remote.MovieRemoteDataSource
import phuchh.sunasterisk.projectmoviedb.databinding.ActivityDetailsBinding
import phuchh.sunasterisk.projectmoviedb.ui.movie.MovieDetailsFragment
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

    override fun initComponent(viewBinding: ActivityDetailsBinding, savedInstanceState: Bundle?) {
        this.viewBinding = viewBinding
        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        initViewModel()
      //  observeViewModel(movieId)
        initTabs(viewBinding, movieId)
        viewBinding.btnDetailsBack.setOnClickListener { onBackPressed() }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(DetailsViewModel::class.java)
    }


    private fun initTabs(binding: ActivityDetailsBinding, movieId: Int) {
        val adapter = DetailsViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MovieDetailsFragment.newInstance(movieId), TITLE_DETAILS)
        val viewPager = binding.pagerDetails
        viewPager.adapter = adapter
        binding.tabDetails.setupWithViewPager(viewPager)
    }

//    private fun observeViewModel(movieId: Int) {
//        viewModel.getMovieDetails(movieId).observe(this,
//            Observer<Movie> { movie ->
//                if (movie != null) {
//                    viewBinding.textMovieTitle.text = movie.title
//                }
//            })
//    }
}

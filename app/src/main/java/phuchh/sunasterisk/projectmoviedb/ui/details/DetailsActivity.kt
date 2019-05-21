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
    private var movieId = 0

    override fun initComponent(viewBinding: ActivityDetailsBinding, savedInstanceState: Bundle?) {
        this.viewBinding = viewBinding
        initViewModel()
        observeViewModel()
        youTubeFragment = supportFragmentManager.findFragmentById(R.id.fragmentYoutube) as YouTubeFragment
        setBtnOnClick()
    }

    override fun onResume() {
        loadData()
        super.onResume()
    }

    override fun onNewIntent(intent: Intent?) {
        setIntent(intent)
        super.onNewIntent(intent)
    }

    override fun onBackPressed() {
        if (youTubeFragment.isFullscreen) {
            youTubeFragment.exitFullscreen()
            return
        }
        super.onBackPressed()
    }

    private fun loadData() {
        movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        viewModel.getMovieDetails(movieId)
        viewModel.isFavorite(movieId)
    }

    private fun setBtnOnClick() {
        viewBinding.btnDetailsBack.setOnClickListener { onBackPressed() }
        viewBinding.btnDetailsLike.setOnClickListener {
            viewModel.addFavorite(movie)
            showToast(ADDED_MSG)
        }
        viewBinding.btnDetailsDislike.setOnClickListener {
            viewModel.deleteFavorite(movie)
            showToast(REMOVED_MSG)

        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders
            .of(this, ViewModelFactory.getInstance(application))
            .get(DetailsViewModel::class.java)
    }

    private fun initTabs() {
        val adapter = DetailsViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MovieDetailsFragment.newInstance(movieId), TITLE_DETAILS)
        adapter.addFragment(CastFragment.newInstance(movieId), TITLE_CAST)
        adapter.addFragment(ProducerFragment.newInstance(movieId), TITLE_PRODUCERS)
        val viewPager = viewBinding.pagerDetails
        viewPager.adapter = adapter
        viewBinding.tabDetails.setupWithViewPager(viewPager)
    }

    private fun observeViewModel() {
        viewModel.run {
            movie.observe(this@DetailsActivity, Observer {
                it?.let {
                    this@DetailsActivity.movie = it
                    updateUI()
                }
            })
            isFavorite.observe(this@DetailsActivity, Observer {
                viewBinding.btnDetailsLike.visibility = if (it!!) View.INVISIBLE else View.VISIBLE
                viewBinding.btnDetailsDislike.visibility = if (!it) View.GONE else View.VISIBLE
            })
            error.observe(this@DetailsActivity, Observer { it?.let { showToast(it) } })
        }
    }

    private fun updateUI() {
        viewBinding.movie = movie
        initTabs()
        val trailer = movie.videoResult!!.videos!!.filter { it.type.equals("trailer", true) }
        if (trailer.isNotEmpty()) {
            youTubeFragment.setTrailerKey(trailer[0].key!!)
            return
        }
        youTubeFragment.setTrailerKey(" ")
        showToast("No Trailer")
    }
}

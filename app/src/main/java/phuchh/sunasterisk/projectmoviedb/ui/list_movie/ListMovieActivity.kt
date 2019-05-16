package phuchh.sunasterisk.projectmoviedb.ui.list_movie

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.AdapterCallback
import phuchh.sunasterisk.projectmoviedb.adapter.MovieRecyclerAdapter
import phuchh.sunasterisk.projectmoviedb.base.BaseActivity
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.ApiResponse
import phuchh.sunasterisk.projectmoviedb.databinding.ActivityListMovieBinding
import phuchh.sunasterisk.projectmoviedb.ui.details.DetailsActivity
import phuchh.sunasterisk.projectmoviedb.utils.ViewModelFactory

class ListMovieActivity : BaseActivity<ActivityListMovieBinding, ListMovieViewModel>() {
    companion object {
        private const val EXTRA_GENRE_ID = "phuchh.sunasterisk.projectmoviedb.extras.EXTRA_GENRE_ID"

        fun getIntent(context: Context, genreId: Int): Intent {
            val intent = Intent(context, ListMovieActivity::class.java)
            intent.putExtra(EXTRA_GENRE_ID, genreId)
            return intent
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_list_movie
    override lateinit var viewModel: ListMovieViewModel
    private lateinit var viewBinding: ActivityListMovieBinding
    private lateinit var movieAdapter: MovieRecyclerAdapter

    override fun initComponent(viewBinding: ActivityListMovieBinding, savedInstanceState: Bundle?) {
        this.viewBinding = viewBinding
        val genreId = intent.getIntExtra(ListMovieActivity.EXTRA_GENRE_ID, 0)
        initViewModel()
        initAdapter()
        observeViewModel(genreId, 1)
    }

    private fun initAdapter() {
        movieAdapter = MovieRecyclerAdapter(movieClickCallback)
        movieAdapter.setLayoutRes(R.layout.item_list_movie)
        viewBinding.recyclerListMovie.adapter = movieAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders
            .of(this, ViewModelFactory.getInstance(application))
            .get(ListMovieViewModel::class.java)
    }

    private fun observeViewModel(genreId: Int, page: Int) {
        viewModel.getMoviesByGenre(genreId, page).observe(this,
            Observer {
                if (it != null) {
                    updateUI(it)
                }
            })
    }

    private fun updateUI(response: ApiResponse<List<Movie>>?) {
        if (response != null) {
            val error: Throwable? = response.error
            val movies: List<Movie>? = response.result
            if (error != null) {
                showToast(error.message!!)
                return
            }
            movieAdapter.setMovies(movies!!)
        }
    }

    private val movieClickCallback = object : AdapterCallback {
        override fun onItemClick(id: Int) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                startActivity(DetailsActivity.getIntent(this@ListMovieActivity, id))
            }
        }
    }
}

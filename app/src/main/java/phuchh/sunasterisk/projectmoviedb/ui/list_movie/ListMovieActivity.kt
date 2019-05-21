package phuchh.sunasterisk.projectmoviedb.ui.list_movie

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.AdapterCallback
import phuchh.sunasterisk.projectmoviedb.adapter.DataRecyclerAdapter
import phuchh.sunasterisk.projectmoviedb.base.BaseActivity
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
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
    private lateinit var movieAdapter: DataRecyclerAdapter<Movie>
    private var page = 1

    override fun initComponent(viewBinding: ActivityListMovieBinding, savedInstanceState: Bundle?) {
        this.viewBinding = viewBinding
        val genreId = intent.getIntExtra(EXTRA_GENRE_ID, 0)
        initToolbar()
        initViewModel()
        initAdapter()
        observeViewModel()
        viewModel.getMoviesByGenre(genreId, page)
        loadMore(genreId)
    }

    private fun initToolbar() {
        viewBinding.toolbarListMovie.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initAdapter() {
        movieAdapter = DataRecyclerAdapter(movieClickCallback, R.layout.item_list_movie)
        viewBinding.recyclerListMovie.adapter = movieAdapter
    }

    private fun loadMore(id: Int) {
        viewBinding.nestedscrollListMovie.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    page++
                    viewModel.getMoviesByGenre(id, page)
                }
            })
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders
            .of(this, ViewModelFactory.getInstance(application))
            .get(ListMovieViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.run {
            movies.observe(this@ListMovieActivity, Observer {
                it?.let { movieAdapter.addData(it) }
            })
            viewModel.error.observe(this@ListMovieActivity, Observer { it?.let { showToast(it) } })
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

package phuchh.sunasterisk.projectmoviedb.ui.category

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
import phuchh.sunasterisk.projectmoviedb.databinding.ActivityCategoryBinding
import phuchh.sunasterisk.projectmoviedb.ui.details.DetailsActivity
import phuchh.sunasterisk.projectmoviedb.utils.ViewModelFactory

class CategoryActivity : BaseActivity<ActivityCategoryBinding, CategoryViewModel>() {
    companion object {
        private const val EXTRA_CATEGORY_TYPE = "phuchh.sunasterisk.projectmoviedb.extras.EXTRA_CATEGORY_TYPE"
        private const val TOP_RATE_MOVIES = "TOP RATE MOVIES"
        private const val POPULAR_MOVIES = "POPULAR MOVIES"
        private const val UP_COMING_MOVIES = "UP COMING MOVIES"
        private const val PLAYING_MOVIES = "PLAYING MOVIES"
        const val POPULAR = 1
        const val PLAYING = 2
        const val UPCOMING = 3
        const val TOP_RATE = 4

        fun getIntent(context: Context, type: Int): Intent {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra(EXTRA_CATEGORY_TYPE, type)
            return intent
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_category
    override lateinit var viewModel: CategoryViewModel
    private lateinit var viewBinding: ActivityCategoryBinding
    private lateinit var movieAdapter: DataRecyclerAdapter<Movie>
    private var page = 1
    private var type = 0

    override fun initComponent(viewBinding: ActivityCategoryBinding, savedInstanceState: Bundle?) {
        this.viewBinding = viewBinding
        type = intent.getIntExtra(EXTRA_CATEGORY_TYPE, 0)
        initToolbar()
        initViewModel()
        initAdapter()
        loadData()
        observeViewModel()
        loadMore()
    }

    private fun initToolbar() {
        viewBinding.toolbarCategory.setNavigationOnClickListener { onBackPressed() }
        if (type == POPULAR) {
            viewBinding.toolbarCategory.title = POPULAR_MOVIES
            return
        }
        if (type == PLAYING) {
            viewBinding.toolbarCategory.title = PLAYING_MOVIES
            return
        }
        if (type == UPCOMING) {
            viewBinding.toolbarCategory.title = UP_COMING_MOVIES
            return
        }
        viewBinding.toolbarCategory.title = TOP_RATE_MOVIES
    }

    private fun initAdapter() {
        movieAdapter = DataRecyclerAdapter(movieClickCallback, R.layout.item_list_movie)
        viewBinding.recyclerCategory.adapter = movieAdapter
    }

    private fun loadMore() {
        viewBinding.nestedscrollCategory.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    page++
                    loadData()
                }
            })
    }

    private fun loadData() {
        if (type == POPULAR) {
            viewModel.getPopularMovies(page)
            return
        }
        if (type == PLAYING) {
            viewModel.getPlayingMovies(page)
            return
        }
        if (type == TOP_RATE) {
            viewModel.getTopMovies(page)
            return
        }
        viewModel.getUpComingMovies(page)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders
            .of(this, ViewModelFactory.getInstance(application))
            .get(CategoryViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.run {
            movies.observe(this@CategoryActivity, Observer {
                it?.let { movieAdapter.addData(it) }
            })
            viewModel.error.observe(this@CategoryActivity, Observer { it?.let { showToast(it) } })
        }
    }

    private val movieClickCallback = object : AdapterCallback {
        override fun onItemClick(id: Int) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                startActivity(DetailsActivity.getIntent(this@CategoryActivity, id))
            }
        }
    }
}

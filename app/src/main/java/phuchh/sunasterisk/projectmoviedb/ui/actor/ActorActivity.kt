package phuchh.sunasterisk.projectmoviedb.ui.actor

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
import phuchh.sunasterisk.projectmoviedb.data.model.response.ApiResponse
import phuchh.sunasterisk.projectmoviedb.databinding.ActivityActorBinding
import phuchh.sunasterisk.projectmoviedb.ui.details.DetailsActivity
import phuchh.sunasterisk.projectmoviedb.utils.ViewModelFactory

class ActorActivity : BaseActivity<ActivityActorBinding, ActorViewModel>() {

    companion object {
        private const val EXTRA_CREDIT_ID = "phuchh.sunasterisk.projectmoviedb.extras.EXTRA_CREDIT_ID"

        fun getIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, ActorActivity::class.java)
            intent.putExtra(EXTRA_CREDIT_ID, id)
            return intent
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_actor
    override lateinit var viewModel: ActorViewModel
    private lateinit var viewBinding: ActivityActorBinding
    private lateinit var movieAdapter: DataRecyclerAdapter<Movie>
    private var page = 1
    private var hasMore = true

    override fun initComponent(viewBinding: ActivityActorBinding, savedInstanceState: Bundle?) {
        this.viewBinding = viewBinding
        val id = intent.getIntExtra(EXTRA_CREDIT_ID, 0)
        initViewModel()
        initToolbar()
        initAdapter()
        observeViewModel(id)
        viewModel.getProfile(id)
        viewModel.getMoviesByActor(id, page)
        loadMore(id)
    }

    private fun initToolbar() {
        viewBinding.toolbarActor.setNavigationOnClickListener { onBackPressed() }
    }

    private fun loadMore(id: Int) {
        viewBinding.scrollActor.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight && hasMore) {
                    page++
                    viewModel.getMoviesByActor(id, page)
                }
            })
    }

    private fun initAdapter() {
        movieAdapter = DataRecyclerAdapter(movieClickCallback, R.layout.item_movie)
        viewBinding.recyclerActorMovies.adapter = movieAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders
            .of(this, ViewModelFactory.getInstance(application))
            .get(ActorViewModel::class.java)
    }

    private fun observeViewModel(id: Int) {
        viewModel.run {
            actor.observe(this@ActorActivity, Observer {
                viewBinding.actor = it
            })
            movies.observe(this@ActorActivity, Observer {
                it?.let {
                    if (it.size < 20)
                        hasMore = false
                    movieAdapter.addData(it)
                }
            })
            errors.observe(this@ActorActivity, Observer { message -> message?.let { showToast(it) } })
        }
    }

    private val movieClickCallback = object : AdapterCallback {
        override fun onItemClick(id: Int) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                startActivity(DetailsActivity.getIntent(this@ActorActivity, id))
            }
        }
    }
}

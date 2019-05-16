package phuchh.sunasterisk.projectmoviedb.ui.actor

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.AdapterCallback
import phuchh.sunasterisk.projectmoviedb.adapter.DataRecyclerAdapter
import phuchh.sunasterisk.projectmoviedb.base.BaseActivity
import phuchh.sunasterisk.projectmoviedb.data.model.Actor
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.ApiResponse
import phuchh.sunasterisk.projectmoviedb.databinding.ActivityActorBinding
import phuchh.sunasterisk.projectmoviedb.ui.details.DetailsActivity
import phuchh.sunasterisk.projectmoviedb.utils.BindingUtils
import phuchh.sunasterisk.projectmoviedb.utils.StringUtils
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

    override fun initComponent(viewBinding: ActivityActorBinding, savedInstanceState: Bundle?) {
        this.viewBinding = viewBinding
        val id = intent.getIntExtra(EXTRA_CREDIT_ID, 0)
        initViewModel()
        initAdapter()
        observeViewModel(id)
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
        viewModel.apply {
            getProfile(id).observe(this@ActorActivity,
                Observer<ApiResponse<Actor>> {
                    if (!hasError(it)) {
                        setData(it!!.result!!)
                    }
                })
            getMoviesByActor(id, 1).observe(this@ActorActivity, Observer {
                if (!hasError(it)) {
                    movieAdapter.setData(it!!.result!!)
                }
            })
        }
    }

    private fun <T> hasError(response: ApiResponse<T>?): Boolean {
        if (response == null) {
            return true
        }
        val error: Throwable? = response.error
        if (error != null) {
            showToast(error.message!!)
            return true
        }
        return false
    }

    private val movieClickCallback = object : AdapterCallback {
        override fun onItemClick(id: Int) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                startActivity(DetailsActivity.getIntent(this@ActorActivity, id))
            }
        }
    }

    private fun setData(actor: Actor) {
        viewBinding.textActorName.text = actor.name
        viewBinding.textActorBorn.text = StringUtils.getActorPOB(actor)
        viewBinding.textActorDepartment.text = actor.department
        viewBinding.textActorBio.text = actor.biography
        BindingUtils.bindImage(viewBinding.imageActorPoster, actor.profilePath)
        viewBinding.textActorDied.text = actor.deathDay
    }
}

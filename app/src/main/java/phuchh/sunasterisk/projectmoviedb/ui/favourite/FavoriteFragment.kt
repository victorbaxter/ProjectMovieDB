package phuchh.sunasterisk.projectmoviedb.ui.favourite


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.AdapterCallback
import phuchh.sunasterisk.projectmoviedb.adapter.DataRecyclerAdapter
import phuchh.sunasterisk.projectmoviedb.base.BaseFragment
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.databinding.FragmentFavoriteBinding
import phuchh.sunasterisk.projectmoviedb.ui.details.DetailsActivity
import phuchh.sunasterisk.projectmoviedb.utils.ViewModelFactory

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {

    override lateinit var viewModel: FavoriteViewModel
    override fun getLayoutRes(): Int = R.layout.fragment_favorite
    private lateinit var viewBinding: FragmentFavoriteBinding
    private lateinit var movieAdapter: DataRecyclerAdapter<Movie>

    companion object {
        fun getInstance() = FavoriteFragment()
    }

    override fun initComponent(viewBinding: FragmentFavoriteBinding, savedInstanceState: Bundle?) {
        this.viewBinding = viewBinding
        initViewModel()
        initAdapter()
        observeViewModel(id)
    }

    private fun initAdapter() {
        movieAdapter = DataRecyclerAdapter(movieClickCallback, R.layout.item_list_movie)
        viewBinding.recyclerFavorite.adapter = movieAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(activity!!.application))
            .get(FavoriteViewModel::class.java)
    }

    private fun observeViewModel(movieId: Int) {
        viewModel.favorite.observe(viewLifecycleOwner,
            Observer<List<Movie>> {
                it?.let { movies ->
                    movieAdapter.setData(movies)
                }
            })

    }

    private val movieClickCallback = object : AdapterCallback {
        override fun onItemClick(id: Int) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                startActivity(DetailsActivity.getIntent(context!!, id))
            }
        }
    }
}

package phuchh.sunasterisk.projectmoviedb.ui.home


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.AdapterCallback
import phuchh.sunasterisk.projectmoviedb.adapter.MovieRecyclerAdapter
import phuchh.sunasterisk.projectmoviedb.base.BaseFragment
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository
import phuchh.sunasterisk.projectmoviedb.data.source.local.MovieLocalDataSource
import phuchh.sunasterisk.projectmoviedb.data.source.remote.MovieRemoteDataSource
import phuchh.sunasterisk.projectmoviedb.databinding.FragmentHomeBinding
import phuchh.sunasterisk.projectmoviedb.ui.details.DetailsActivity


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private lateinit var popularAdapter: MovieRecyclerAdapter
    private lateinit var playingAdapter: MovieRecyclerAdapter
    private lateinit var topAdapter: MovieRecyclerAdapter
    private lateinit var comingAdapter: MovieRecyclerAdapter
    override lateinit var viewModel: HomeViewModel

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun initComponent(viewBinding: FragmentHomeBinding, savedInstanceState: Bundle?) {
        initViewModel()
        popularAdapter = MovieRecyclerAdapter(movieClickCallback)
        playingAdapter = MovieRecyclerAdapter(movieClickCallback)
        topAdapter = MovieRecyclerAdapter(movieClickCallback)
        comingAdapter = MovieRecyclerAdapter(movieClickCallback)
        viewBinding.recyclerPopularMovies.adapter = popularAdapter
        viewBinding.recyclerComingMovies.adapter = comingAdapter
        viewBinding.recyclerPlayingMovies.adapter = playingAdapter
        viewBinding.recyclerTopMovies.adapter = topAdapter
        observeViewModel()
    }

    private fun initViewModel() {
        val viewModelFactory = HomeViewModel.Factory(
            MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance(context!!),
                MovieLocalDataSource.getInstance()
            )
        )
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.popularMovies.observe(this,
            Observer<List<Movie>> { movies ->
                if (movies != null) {
                    popularAdapter.setMovies(movies)
                }
            })

        viewModel.playingMovies.observe(this,
            Observer<List<Movie>> { movies ->
                if (movies != null) {
                    playingAdapter.setMovies(movies)
                }
            })
        viewModel.topMovies.observe(this,
            Observer<List<Movie>> { movies ->
                if (movies != null) {
                    topAdapter.setMovies(movies)
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


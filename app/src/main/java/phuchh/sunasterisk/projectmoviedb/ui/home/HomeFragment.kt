package phuchh.sunasterisk.projectmoviedb.ui.home


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.AdapterCallback
import phuchh.sunasterisk.projectmoviedb.adapter.MovieRecyclerAdapter
import phuchh.sunasterisk.projectmoviedb.adapter.SliderAdapter
import phuchh.sunasterisk.projectmoviedb.base.BaseFragment
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.ApiResponse
import phuchh.sunasterisk.projectmoviedb.databinding.FragmentHomeBinding
import phuchh.sunasterisk.projectmoviedb.ui.details.DetailsActivity
import phuchh.sunasterisk.projectmoviedb.utils.ViewModelFactory
import java.util.*


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private lateinit var popularAdapter: MovieRecyclerAdapter
    private lateinit var playingAdapter: MovieRecyclerAdapter
    private lateinit var topAdapter: MovieRecyclerAdapter
    private lateinit var comingAdapter: MovieRecyclerAdapter
    private lateinit var pagerLatest: ViewPager
    private lateinit var indicator: TabLayout
    private lateinit var latestMovies: List<Movie>
    private lateinit var sliderAdapter: SliderAdapter
    override lateinit var viewModel: HomeViewModel

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun initComponent(viewBinding: FragmentHomeBinding, savedInstanceState: Bundle?) {
        initViewModel()
        popularAdapter = MovieRecyclerAdapter(movieClickCallback)
        playingAdapter = MovieRecyclerAdapter(movieClickCallback)
        topAdapter = MovieRecyclerAdapter(movieClickCallback)
        comingAdapter = MovieRecyclerAdapter(movieClickCallback)
        pagerLatest = viewBinding.pagerLatest
        indicator = viewBinding.indicator
        sliderAdapter = SliderAdapter(context!!)
        viewBinding.recyclerPopularMovies.adapter = popularAdapter
        viewBinding.recyclerComingMovies.adapter = comingAdapter
        viewBinding.recyclerPlayingMovies.adapter = playingAdapter
        viewBinding.recyclerTopMovies.adapter = topAdapter
        observeViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(activity!!.application))
            .get(HomeViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.run {
            popularMovies.observe(viewLifecycleOwner,
                Observer<ApiResponse<List<Movie>>> {
                    updateUI(it, popularAdapter)
                })
            playingMovies.observe(viewLifecycleOwner,
                Observer<ApiResponse<List<Movie>>> {
                    updateUI(it, playingAdapter)
                })
            topMovies.observe(viewLifecycleOwner,
                Observer<ApiResponse<List<Movie>>> {
                    updateUI(it, topAdapter)
                })
            latestMovies.observe(viewLifecycleOwner,
                Observer<ApiResponse<List<Movie>>> {
                    if (it != null) {
                        val error: Throwable? = it.error
                        val movies: List<Movie>? = it.result
                        if (error != null) {
                            showToast(error.message!!)

                        } else {
                            sliderAdapter.setMovies(movies!!.subList(0, 7))
                            this@HomeFragment.latestMovies = movies.subList(0, 7)
                            initSlider()
                        }
                    }
                })
        }
    }

    private fun updateUI(response: ApiResponse<List<Movie>>?, adapter: MovieRecyclerAdapter) {
        if (response != null) {
            val error: Throwable? = response.error
            val movies: List<Movie>? = response.result
            if (error != null) {
                showToast(error.message!!)
                return
            }
            adapter.setMovies(movies!!)
        }
    }

    private val movieClickCallback = object : AdapterCallback {
        override fun onItemClick(id: Int) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                startActivity(DetailsActivity.getIntent(context!!, id))
            }
        }
    }

    private fun initSlider() {
        pagerLatest.adapter = sliderAdapter
        indicator.setupWithViewPager(pagerLatest)
        Timer().scheduleAtFixedRate(SliderTimer(), 4000, 6000)
    }

    inner class SliderTimer : TimerTask() {
        override fun run() {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                activity!!.runOnUiThread {
                    if (pagerLatest.currentItem < latestMovies.size - 1) {
                        pagerLatest.currentItem = pagerLatest.currentItem + 1
                    } else {
                        pagerLatest.currentItem = 0
                    }
                }
            }
        }
    }
}


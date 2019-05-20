package phuchh.sunasterisk.projectmoviedb.ui.home


import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.AdapterCallback
import phuchh.sunasterisk.projectmoviedb.adapter.DataRecyclerAdapter
import phuchh.sunasterisk.projectmoviedb.adapter.SliderAdapter
import phuchh.sunasterisk.projectmoviedb.base.BaseFragment
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.ApiResponse
import phuchh.sunasterisk.projectmoviedb.databinding.FragmentHomeBinding
import phuchh.sunasterisk.projectmoviedb.ui.details.DetailsActivity
import phuchh.sunasterisk.projectmoviedb.ui.search.SearchActivity
import phuchh.sunasterisk.projectmoviedb.utils.ViewModelFactory
import java.util.*


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {


    private lateinit var popularAdapter: DataRecyclerAdapter<Movie>
    private lateinit var playingAdapter: DataRecyclerAdapter<Movie>
    private lateinit var topAdapter: DataRecyclerAdapter<Movie>
    private lateinit var comingAdapter: DataRecyclerAdapter<Movie>
    private lateinit var pagerLatest: ViewPager
    private lateinit var indicator: TabLayout
    private lateinit var latestMovies: List<Movie>
    private lateinit var sliderAdapter: SliderAdapter
    override lateinit var viewModel: HomeViewModel
    private val layoutRes = R.layout.item_movie

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun initComponent(viewBinding: FragmentHomeBinding, savedInstanceState: Bundle?) {
        initViewModel()
        initAdapter(viewBinding)
        pagerLatest = viewBinding.pagerLatest
        indicator = viewBinding.indicator
        sliderAdapter = SliderAdapter(movieClickCallback, context!!)
        observeViewModel()
        viewBinding.layoutSearch.setOnClickListener(searchOnClick)
    }

    private fun initAdapter(viewBinding: FragmentHomeBinding) {
        popularAdapter = DataRecyclerAdapter(movieClickCallback, layoutRes)
        playingAdapter = DataRecyclerAdapter(movieClickCallback, layoutRes)
        topAdapter = DataRecyclerAdapter(movieClickCallback, layoutRes)
        comingAdapter = DataRecyclerAdapter(movieClickCallback, layoutRes)

        viewBinding.recyclerPopularMovies.adapter = popularAdapter
        viewBinding.recyclerComingMovies.adapter = comingAdapter
        viewBinding.recyclerPlayingMovies.adapter = playingAdapter
        viewBinding.recyclerTopMovies.adapter = topAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(activity!!.application))
            .get(HomeViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.run {
            popularMovies.observe(viewLifecycleOwner,
                Observer {
                    updateUI(it, popularAdapter)
                })
            playingMovies.observe(viewLifecycleOwner,
                Observer {
                    updateUI(it, playingAdapter)
                })
            topMovies.observe(viewLifecycleOwner,
                Observer {
                    updateUI(it, topAdapter)
                })
            upComingMovies.observe(viewLifecycleOwner,
                Observer {
                    updateUI(it, comingAdapter)
                })
            latestMovies.observe(viewLifecycleOwner,
                Observer {
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

    private fun updateUI(response: ApiResponse<List<Movie>>?, adapter: DataRecyclerAdapter<Movie>) {
        if (response != null) {
            val error: Throwable? = response.error
            val movies: List<Movie>? = response.result
            if (error != null) {
                showToast(error.message!!)
                return
            }
            adapter.setData(movies!!)
        }
    }

    private val movieClickCallback = object : AdapterCallback {
        override fun onItemClick(id: Int) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                startActivity(DetailsActivity.getIntent(context!!, id))
            }
        }
    }

    private val searchOnClick = View.OnClickListener { startActivity(SearchActivity.getIntent(context!!)) }

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
                        pagerLatest.currentItem++
                        return@runOnUiThread
                    }
                    pagerLatest.currentItem = 0
                }
            }
        }
    }
}


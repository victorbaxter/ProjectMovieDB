package phuchh.sunasterisk.projectmoviedb.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.MenuItem
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.SliderAdapter
import phuchh.sunasterisk.projectmoviedb.base.BaseActivity
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository
import phuchh.sunasterisk.projectmoviedb.data.source.local.MovieLocalDataSource
import phuchh.sunasterisk.projectmoviedb.data.source.remote.MovieRemoteDataSource
import phuchh.sunasterisk.projectmoviedb.databinding.ActivityMainBinding
import phuchh.sunasterisk.projectmoviedb.ui.home.HomeFragment
import phuchh.sunasterisk.projectmoviedb.utils.Navigation
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    companion object {
        const val TAG = "NAV"
    }

    override fun getLayoutRes(): Int = R.layout.activity_main
    override lateinit var viewModel: MainViewModel
    private var navigation: Int = Navigation.HOME
    private val sliderAdapter = SliderAdapter(this)
    private lateinit var pagerLatest: ViewPager
    private lateinit var indicator: TabLayout
    private lateinit var latestMovies: List<Movie>

    override fun initComponent(viewBinding: ActivityMainBinding, savedInstanceState: Bundle?) {
        initViewModel()
        pagerLatest = viewBinding.pagerLatest
        indicator = viewBinding.indicator
        val navigation: BottomNavigationView = findViewById(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        showDefaultHome()
        observeViewModel()
    }

    private fun initViewModel() {
        val viewModelFactory = MainViewModel.Factory(
            MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance(this),
                MovieLocalDataSource.getInstance()
            )
        )
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private fun showDefaultHome() {
        val fragment = HomeFragment()
        replaceFragment(fragment, R.id.frame_container, false, TAG)
    }

    private val onNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            val fragment: Fragment
            when (item.itemId) {
                R.id.navigation_home -> {
                    if (navigation != Navigation.HOME) {
                        navigation = Navigation.HOME
                        fragment = HomeFragment()
                        replaceFragment(fragment, R.id.frame_container, false, TAG)
                    }
                    return true
                }
                R.id.navigation_genre -> {
                    //TODO: Update genre
                    navigation = Navigation.GENRE
                    return true
                }
                R.id.navigation_fav -> {
                    //TODO: Update fav
                    navigation = Navigation.FAVOURITE
                    return true
                }
            }
            return false
        }
    }

    private fun observeViewModel() {
        viewModel.latestMovies.observe(this,
            Observer<List<Movie>> { movies ->
                if (movies != null) {
                    sliderAdapter.setMovies(movies.subList(0, 7))
                    latestMovies = movies.subList(0, 7)
                    initSlider()
                }
            })
    }

    private fun initSlider() {
        pagerLatest.adapter = sliderAdapter
        indicator.setupWithViewPager(pagerLatest)
        Timer().scheduleAtFixedRate(SliderTimer(), 4000, 6000)
    }

    inner class SliderTimer : TimerTask() {
        override fun run() {
            this@MainActivity.runOnUiThread {
                if (pagerLatest.currentItem < latestMovies.size - 1) {
                    pagerLatest.currentItem = pagerLatest.currentItem + 1
                } else {
                    pagerLatest.currentItem = 0
                }
            }
        }
    }
}

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
import phuchh.sunasterisk.projectmoviedb.databinding.ActivityMainBinding
import phuchh.sunasterisk.projectmoviedb.ui.genre.GenreFragment
import phuchh.sunasterisk.projectmoviedb.ui.home.HomeFragment
import phuchh.sunasterisk.projectmoviedb.utils.Navigation
import phuchh.sunasterisk.projectmoviedb.utils.ViewModelFactory
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    companion object {
        const val TAG = "NAV"
    }

    override fun getLayoutRes(): Int = R.layout.activity_main
    override lateinit var viewModel: MainViewModel
    private var navigation: Int = Navigation.HOME

    override fun initComponent(viewBinding: ActivityMainBinding, savedInstanceState: Bundle?) {
        initViewModel()
        val navigation: BottomNavigationView = findViewById(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        showDefaultHome()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(MainViewModel::class.java)
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
                    if (navigation != Navigation.GENRE) {
                        navigation = Navigation.GENRE
                        fragment = GenreFragment()
                        replaceFragment(fragment, R.id.frame_container, false, TAG)
                    }
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
}

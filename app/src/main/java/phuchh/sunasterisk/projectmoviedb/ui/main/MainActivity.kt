package phuchh.sunasterisk.projectmoviedb.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.base.BaseActivity
import phuchh.sunasterisk.projectmoviedb.databinding.ActivityMainBinding
import phuchh.sunasterisk.projectmoviedb.ui.favourite.FavoriteFragment
import phuchh.sunasterisk.projectmoviedb.ui.genre.GenreFragment
import phuchh.sunasterisk.projectmoviedb.ui.home.HomeFragment
import phuchh.sunasterisk.projectmoviedb.utils.Navigation
import phuchh.sunasterisk.projectmoviedb.utils.ViewModelFactory

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    companion object {
        const val TAG = "NAV"
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    private var pressedTwice = false

    override fun getLayoutRes(): Int = R.layout.activity_main
    override lateinit var viewModel: MainViewModel
    private var navigation: Int = Navigation.HOME

    override fun initComponent(viewBinding: ActivityMainBinding, savedInstanceState: Bundle?) {
        initViewModel()
        val navigation: BottomNavigationView = findViewById(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        showDefaultHome()
    }

    override fun onBackPressed() {
        if (pressedTwice) {
            super.onBackPressed()
            return
        }
        pressedTwice = true
        showToast("Press back again to exit the app")

        Handler().postDelayed({ pressedTwice = false }, 2000)
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(MainViewModel::class.java)
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
                    fragment = FavoriteFragment()
                    replaceFragment(fragment, R.id.frame_container, false, TAG)
                    navigation = Navigation.FAVOURITE
                    return true
                }
            }
            return false
        }
    }
}

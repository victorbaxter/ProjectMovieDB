package phuchh.sunasterisk.projectmoviedb.ui.search

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.SearchView
import android.view.View
import android.view.inputmethod.InputMethodManager
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.AdapterCallback
import phuchh.sunasterisk.projectmoviedb.adapter.DataRecyclerAdapter
import phuchh.sunasterisk.projectmoviedb.base.BaseActivity
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.databinding.ActivitySearchBinding
import phuchh.sunasterisk.projectmoviedb.ui.details.DetailsActivity
import phuchh.sunasterisk.projectmoviedb.utils.ViewModelFactory

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(), SearchView.OnQueryTextListener {

    companion object {
        fun getIntent(context: Context) = Intent(context, SearchActivity::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.activity_search
    override lateinit var viewModel: SearchViewModel
    private lateinit var viewBinding: ActivitySearchBinding
    private lateinit var movieAdapter: DataRecyclerAdapter<Movie>
    private var page = 1
    private lateinit var query: String

    override fun initComponent(viewBinding: ActivitySearchBinding, savedInstanceState: Bundle?) {
        this.viewBinding = viewBinding
        initViewModel()
        initAdapter()
        initBackBtn()
        loadMore()
        observeViewModel()
        viewBinding.textSearch.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!!.isEmpty()) return false
        this.query = query
        movieAdapter.clearAll()
        viewModel.searchMovies(query, page)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query!!.isEmpty()) return false
        this.query = query
        movieAdapter.clearAll()
        viewModel.searchMovies(query, page)
        return true
    }

    private fun initBackBtn() {
        viewBinding.buttonSearchBack.setOnClickListener {
            val editText = viewBinding.textSearch
            if (editText.hasFocus()) {
                editText.clearFocus()
                return@setOnClickListener
            }
            onBackPressed()
        }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun initAdapter() {
        movieAdapter = DataRecyclerAdapter(movieClickCallback, R.layout.item_list_movie)
        viewBinding.recyclerSearchMovies.adapter = movieAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders
            .of(this, ViewModelFactory.getInstance(application))
            .get(SearchViewModel::class.java)
        viewBinding.viewModel = viewModel
    }

    private fun observeViewModel() {
        viewModel.run {
            movies.observe(this@SearchActivity, Observer {
                it?.let {
                    movieAdapter.addData(it)
                    return@Observer
                }
                checkNoResult()
            })
            error.observe(this@SearchActivity, Observer { it?.let { showToast(it) } })
        }
    }

    private fun loadMore() {
        viewBinding.nestedscrollSearch.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    page++
                    viewModel.searchMovies(query, page)
                }
            })
    }

    private val movieClickCallback = object : AdapterCallback {
        override fun onItemClick(id: Int) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                startActivity(DetailsActivity.getIntent(this@SearchActivity, id))
            }
        }
    }

    private fun checkNoResult() = showToast("No Result Found")
}

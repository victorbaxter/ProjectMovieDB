package phuchh.sunasterisk.projectmoviedb.ui.search

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.AdapterCallback
import phuchh.sunasterisk.projectmoviedb.adapter.DataRecyclerAdapter
import phuchh.sunasterisk.projectmoviedb.base.BaseActivity
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.ApiResponse
import phuchh.sunasterisk.projectmoviedb.databinding.ActivitySearchBinding
import phuchh.sunasterisk.projectmoviedb.ui.details.DetailsActivity
import phuchh.sunasterisk.projectmoviedb.utils.ViewModelFactory

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>() {

    companion object {
        fun getIntent(context: Context): Intent {
            val intent = Intent(context, SearchActivity::class.java)
            return intent
        }
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
        searchMovies()
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
        viewModel.searchMovies(query, page).observe(this@SearchActivity, Observer {
            if (!hasError(it)) {
                movieAdapter.addData(it!!.result!!)
            }
        })
    }

    private fun searchMovies(){
        viewBinding.textSearch.afterTextChanged {
            query = it
            movieAdapter.clearAll()
            observeViewModel()
        }
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
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
}

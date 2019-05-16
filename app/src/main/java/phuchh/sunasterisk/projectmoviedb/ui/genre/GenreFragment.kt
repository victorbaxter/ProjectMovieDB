package phuchh.sunasterisk.projectmoviedb.ui.genre

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.adapter.AdapterCallback
import phuchh.sunasterisk.projectmoviedb.adapter.DataRecyclerAdapter
import phuchh.sunasterisk.projectmoviedb.base.BaseFragment
import phuchh.sunasterisk.projectmoviedb.data.model.Genre
import phuchh.sunasterisk.projectmoviedb.databinding.FragmentGenreBinding
import phuchh.sunasterisk.projectmoviedb.ui.list_movie.ListMovieActivity
import phuchh.sunasterisk.projectmoviedb.utils.ViewModelFactory

class GenreFragment : BaseFragment<FragmentGenreBinding, GenreViewModel>() {
    companion object {
        private const val GENRE = "Movie Genre"
    }

    override fun getLayoutRes(): Int = R.layout.fragment_genre
    override lateinit var viewModel: GenreViewModel
    private lateinit var viewBinding: FragmentGenreBinding
    private lateinit var genreAdapter: DataRecyclerAdapter<Genre>

    override fun initComponent(viewBinding: FragmentGenreBinding, savedInstanceState: Bundle?) {
        this.viewBinding = viewBinding
        initViewModel()
        genreAdapter = DataRecyclerAdapter(genreClickCallback, R.layout.item_genre)
        viewBinding.recyclerMovieGenre.adapter = genreAdapter
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.genres.observe(viewLifecycleOwner, Observer<List<Genre>> {
            if (it != null) {
                genreAdapter.setData(it)
            }
        })
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(activity!!.application))
            .get(GenreViewModel::class.java)
    }

    private val genreClickCallback = object : AdapterCallback {
        override fun onItemClick(id: Int) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                startActivity(ListMovieActivity.getIntent(context!!, id))
            }
        }
    }
}

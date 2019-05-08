package phuchh.sunasterisk.projectmoviedb.ui.movie


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.base.BaseFragment
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository
import phuchh.sunasterisk.projectmoviedb.data.source.local.MovieLocalDataSource
import phuchh.sunasterisk.projectmoviedb.data.source.remote.MovieRemoteDataSource
import phuchh.sunasterisk.projectmoviedb.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>() {

    companion object {
        private const val ARGUMENT_ID = "ARGUMENT_ID"
        fun newInstance(id: Int): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            val args = Bundle()
            args.putInt(ARGUMENT_ID, id)
            fragment.arguments = args
            return fragment
        }
    }

    override lateinit var viewModel: MovieDetailsViewModel
    override fun getLayoutRes(): Int = R.layout.fragment_movie_details

    private var movieId: Int = 0

    override fun initComponent(viewBinding: FragmentMovieDetailsBinding, savedInstanceState: Bundle?) {
        getMovieId()
        initViewModel()
    }

    private fun initViewModel() {
        val viewModelFactory = MovieDetailsViewModel.Factory(
            MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance(context!!),
                MovieLocalDataSource.getInstance()
            )
        )
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsViewModel::class.java)
    }

    private fun getMovieId() {
        val args = this.arguments
        if (args != null) {
            movieId = args.getInt(ARGUMENT_ID)
        }
    }

}

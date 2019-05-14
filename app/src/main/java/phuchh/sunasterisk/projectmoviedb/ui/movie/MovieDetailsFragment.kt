package phuchh.sunasterisk.projectmoviedb.ui.movie


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.base.BaseFragment
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository
import phuchh.sunasterisk.projectmoviedb.data.source.local.MovieLocalDataSource
import phuchh.sunasterisk.projectmoviedb.data.source.remote.MovieRemoteDataSource
import phuchh.sunasterisk.projectmoviedb.databinding.FragmentMovieDetailsBinding
import phuchh.sunasterisk.projectmoviedb.utils.BindingUtils
import phuchh.sunasterisk.projectmoviedb.utils.ViewModelFactory

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>() {

    companion object {
        private const val ARGUMENT_ID: String = "ARGUMENT_ID"
        fun newInstance(id: Int) = MovieDetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(ARGUMENT_ID, id)
            }
        }
    }

    override lateinit var viewModel: MovieDetailsViewModel
    override fun getLayoutRes(): Int = R.layout.fragment_movie_details
    private lateinit var viewBinding: FragmentMovieDetailsBinding

    override fun initComponent(viewBinding: FragmentMovieDetailsBinding, savedInstanceState: Bundle?) {
        this.viewBinding = viewBinding
        val id = arguments!!.getInt(ARGUMENT_ID)
        initViewModel()
     //   observeViewModel(id)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(activity!!.application)).get(MovieDetailsViewModel::class.java)
    }

//    private fun observeViewModel(movieId: Int) {
//        viewModel.getMovieDetails(movieId).observe(viewLifecycleOwner,
//            Observer<Movie> { movie ->
//                if (movie != null) {
//                    updateMovieDetails(movie)
//                }
//            })
//
//    }

    private fun updateMovieDetails(movie: Movie) {
        BindingUtils.bindImage(viewBinding.imageDetailsPoster, movie.posterPath!!)
        viewBinding.textDetailsTitle.text = movie.title
        viewBinding.textDetailsDate.text = movie.releaseDate
        viewBinding.textDetailsDuration.text = movie.runtime.toString()
        viewBinding.textDetailsInfo.text = movie.overview
        viewBinding.textDetailsStatus.text = movie.status
        BindingUtils.bindProductionCompany(viewBinding.textDetailsProduction, movie)
    }
}

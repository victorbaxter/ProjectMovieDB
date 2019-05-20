package phuchh.sunasterisk.projectmoviedb.ui.movie


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import phuchh.sunasterisk.projectmoviedb.R
import phuchh.sunasterisk.projectmoviedb.base.BaseFragment
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.ApiResponse
import phuchh.sunasterisk.projectmoviedb.databinding.FragmentMovieDetailsBinding
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
        viewBinding.textDetailsInfo.movementMethod = ScrollingMovementMethod()
        initViewModel()
        observeViewModel(id)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(activity!!.application))
            .get(MovieDetailsViewModel::class.java)
    }

    private fun observeViewModel(movieId: Int) {
        viewModel.getMovieDetails(movieId).observe(viewLifecycleOwner,
            Observer<ApiResponse<Movie>> {
                if (it != null) {
                    updateUI(it)
                }
            })

    }

    private fun updateUI(response: ApiResponse<Movie>?) {
        if (response != null) {
            val error: Throwable? = response.error
            val movie: Movie? = response.result
            if (error != null) {
                showToast(error.message!!)
                return
            }
            viewBinding.movie = movie
            viewBinding.textDetailsTitle.isSelected = true
        }
    }
}

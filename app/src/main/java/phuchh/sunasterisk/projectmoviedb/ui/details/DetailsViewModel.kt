package phuchh.sunasterisk.projectmoviedb.ui.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class DetailsViewModel(private val repository: MovieRepository) : BaseViewModel() {
    //fun getMovieDetails(id: Int): LiveData<Movie> = repository.getMovieDetails(id)
}

package phuchh.sunasterisk.projectmoviedb.ui.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class MovieDetailsViewModel(private val repository: MovieRepository) : BaseViewModel() {
   // fun getMovieDetails(movieId: Int): LiveData<Movie> = repository.getMovieDetails(movieId)
}
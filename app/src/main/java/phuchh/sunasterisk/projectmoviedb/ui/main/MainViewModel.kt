package phuchh.sunasterisk.projectmoviedb.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class MainViewModel(repository: MovieRepository) : BaseViewModel() {
  //  val latestMovies: LiveData<List<Movie>> = repository.getMoviesTrendingByDay()
}
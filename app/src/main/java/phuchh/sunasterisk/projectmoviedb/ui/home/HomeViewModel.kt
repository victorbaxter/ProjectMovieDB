package phuchh.sunasterisk.projectmoviedb.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository


class HomeViewModel(repository: MovieRepository) : BaseViewModel() {
    val popularMovies: LiveData<List<Movie>> = repository.getPopularMovies()
    val playingMovies: LiveData<List<Movie>> = repository.getPlayingMovies()
    val topMovies: LiveData<List<Movie>> = repository.getTopMovies()

    class Factory(private val repository: MovieRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = HomeViewModel(repository) as T

    }
}

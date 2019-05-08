package phuchh.sunasterisk.projectmoviedb.ui.movie

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class MovieDetailsViewModel(repository: MovieRepository) : BaseViewModel() {
    class Factory(private val repository: MovieRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = MovieDetailsViewModel(repository) as T
    }
}
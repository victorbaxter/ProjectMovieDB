package phuchh.sunasterisk.projectmoviedb.ui.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class DetailsViewModel(val repository: MovieRepository) : BaseViewModel() {
    fun getMovieDetails(id: Int): LiveData<Movie> = repository.getMovieDetails(id)
    class Factory(private val repository: MovieRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = DetailsViewModel(repository) as T
    }
}

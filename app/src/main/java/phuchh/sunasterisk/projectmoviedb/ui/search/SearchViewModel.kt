package phuchh.sunasterisk.projectmoviedb.ui.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class SearchViewModel(val repository: MovieRepository) : BaseViewModel() {
    fun searchMovies(query: String, page: Int) = repository.searchMoviesByName(query, page)
}
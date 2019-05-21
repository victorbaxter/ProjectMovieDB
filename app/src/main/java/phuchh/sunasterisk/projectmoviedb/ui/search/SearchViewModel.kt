package phuchh.sunasterisk.projectmoviedb.ui.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class SearchViewModel(val repository: MovieRepository) : BaseViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun searchMovies(query: String, page: Int) {
        val disposable = repository.searchMoviesByName(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _movies.postValue(it.results) }, { _error.postValue(it.message) })
        compositeDisposable.add(disposable)
    }
}
package phuchh.sunasterisk.projectmoviedb.ui.list_movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class ListMovieViewModel(private val repository: MovieRepository) : BaseViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error
    private var hasMore = true

    fun getMoviesByGenre(genreId: Int, page: Int) {
        if (!hasMore) return
        val disposable = repository.getMoviesByGenre(genreId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response -> response?.let {
                    hasMore = it.page < it.totalPage
                    _movies.postValue(it.results)
                }
            }, { _error.postValue(it.message) })
        compositeDisposable.add(disposable)
    }
}
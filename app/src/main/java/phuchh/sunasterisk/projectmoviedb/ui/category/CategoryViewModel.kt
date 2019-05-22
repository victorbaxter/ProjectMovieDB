package phuchh.sunasterisk.projectmoviedb.ui.category

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class CategoryViewModel(val repository: MovieRepository) : BaseViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error
    private var hasMore = true

    fun getPopularMovies(page: Int) {
        if (!hasMore) return
        val disposable = repository.getPopularMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response?.let {
                    hasMore = it.page < it.totalPage
                    _movies.postValue(it.results)
                }
            }, { _error.postValue(it.message) })
        compositeDisposable.add(disposable)
    }

    fun getTopMovies(page: Int) {
        if (!hasMore) return
        val disposable = repository.getTopMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response?.let {
                    hasMore = it.page < it.totalPage
                    _movies.postValue(it.results)
                }
            }, { _error.postValue(it.message) })
        compositeDisposable.add(disposable)
    }

    fun getUpComingMovies(page: Int) {
        if (!hasMore) return
        val disposable = repository.getUpComingMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response?.let {
                    hasMore = it.page < it.totalPage
                    _movies.postValue(it.results)
                }
            }, { _error.postValue(it.message) })
        compositeDisposable.add(disposable)
    }

    fun getPlayingMovies(page: Int) {
        if (!hasMore) return
        val disposable = repository.getPlayingMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response?.let {
                    hasMore = it.page < it.totalPage
                    _movies.postValue(it.results)
                }
            }, { _error.postValue(it.message) })
        compositeDisposable.add(disposable)
    }
}
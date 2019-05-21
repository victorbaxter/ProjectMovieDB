package phuchh.sunasterisk.projectmoviedb.ui.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class MovieDetailsViewModel(private val repository: MovieRepository) : BaseViewModel() {
    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun getMovieDetails(movieId: Int) {
        val disposable = repository.getMovieDetails(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _movie.postValue(it) }, { _error.postValue(it.message) })
        compositeDisposable.add(disposable)
    }
}
package phuchh.sunasterisk.projectmoviedb.ui.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class DetailsViewModel(private val repository: MovieRepository) : BaseViewModel() {
    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    fun getMovieDetails(id: Int) {
        val disposable =
            repository.getMovieDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _movie.postValue(it) }, { _error.postValue(it.message) })
        compositeDisposable.add(disposable)
    }

    fun addFavorite(movie: Movie) {
        val disposable = Observable.just(repository)
            .subscribeOn(Schedulers.io())
            .subscribe {
                it.addFavorite(movie)
                _isFavorite.postValue(true)
            }
        compositeDisposable.add(disposable)
    }

    fun isFavorite(id: Int) {
        val disposable = Observable.just(repository)
            .subscribeOn(Schedulers.io())
            .map { repo -> repo.getFavouriteById(id) }
            .subscribe({ _isFavorite.postValue(it != null) }, { _isFavorite.postValue(false) })
        compositeDisposable.add(disposable)
    }

    fun deleteFavorite(movie: Movie) {
        val disposable = Observable.just(repository)
            .subscribeOn(Schedulers.io())
            .subscribe {
                it.deleteFavorite(movie)
                _isFavorite.postValue(false)
            }
        compositeDisposable.add(disposable)
    }
}

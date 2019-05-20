package phuchh.sunasterisk.projectmoviedb.ui.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.ApiResponse
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class DetailsViewModel(private val repository: MovieRepository) : BaseViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    fun getMovieDetails(id: Int): LiveData<ApiResponse<Movie>> = repository.getMovieDetails(id)
    fun addFavorite(movie: Movie) {
        val disposable =
            Observable.just(repository).subscribeOn(Schedulers.io()).subscribe {
                it.addFavorite(movie)
                _isFavorite.postValue(true)
            }
        compositeDisposable.add(disposable)
    }

    fun isFavorite(id: Int) {
        val disposable = Observable.just(repository).subscribeOn(Schedulers.io())
            .map { repo -> repo.getMovieById(id) }.subscribe({
                _isFavorite.postValue(it != null)
            }, { _isFavorite.postValue(false) })
        compositeDisposable.add(disposable)
    }

    fun deleteFavorite(movie: Movie) {
        val disposable = Observable.just(repository).subscribeOn(Schedulers.io()).subscribe {
            it.deleteFavorite(movie)
            _isFavorite.postValue(false)
        }
        compositeDisposable.add(disposable)
    }

    fun dispose() = compositeDisposable.dispose()
}

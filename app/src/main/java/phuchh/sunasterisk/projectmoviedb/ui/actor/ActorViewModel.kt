package phuchh.sunasterisk.projectmoviedb.ui.actor

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.model.Actor
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class ActorViewModel(val repository: MovieRepository) : BaseViewModel() {
    private val _actor = MutableLiveData<Actor>()
    val actor: LiveData<Actor>
        get() = _actor
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies
    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String>
        get() = _errors

    fun getProfile(id: Int) {
        val disposable =
            repository.getProfile(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _actor.postValue(it) }, { _errors.postValue(it.message) })
        compositeDisposable.add(disposable)
    }

    fun getMoviesByActor(id: Int, page: Int) {
        val disposable =
            repository.getMoviesByActor(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { movieResponse -> movieResponse?.let { _movies.postValue(it.results) } },
                    { _errors.postValue(it.message) })
        compositeDisposable.add(disposable)
    }
}
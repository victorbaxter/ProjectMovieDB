package phuchh.sunasterisk.projectmoviedb.ui.cast

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.model.Cast
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class CastViewModel(val repository: MovieRepository) : BaseViewModel() {
    private val _cast = MutableLiveData<List<Cast>>()
    val cast: LiveData<List<Cast>>
        get() = _cast
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun getCast(movieId: Int) {
        val disposable =
            repository.getCast(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ credits -> credits?.let { _cast.postValue(it.cast) } }, { _error.postValue(it.message) })
        compositeDisposable.add(disposable)
    }
}
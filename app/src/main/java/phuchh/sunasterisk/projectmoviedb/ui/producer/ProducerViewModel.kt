package phuchh.sunasterisk.projectmoviedb.ui.producer

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.model.Crew
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class ProducerViewModel(val repository: MovieRepository) : BaseViewModel() {
    companion object {
        private const val PRODUCER = "producer"
        private const val DIRECTOR = "director"
    }

    private val _producers = MutableLiveData<List<Crew>>()
    val producers: LiveData<List<Crew>>
        get() = _producers
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun getProducers(id: Int) {
        val disposable = repository.getCrew(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { credit ->
                credit.crew?.filter {
                    it.job!!.run {
                        contains(PRODUCER, true) || contains(DIRECTOR, true)
                    }
                }
            }
            .subscribe({ _producers.postValue(it) }, { _error.postValue(it.message) })
        compositeDisposable.add(disposable)
    }
}
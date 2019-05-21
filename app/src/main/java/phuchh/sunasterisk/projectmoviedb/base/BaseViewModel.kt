package phuchh.sunasterisk.projectmoviedb.base

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel(), LifecycleObserver {
    protected val compositeDisposable = CompositeDisposable()
    fun dispose() = compositeDisposable.dispose()
}

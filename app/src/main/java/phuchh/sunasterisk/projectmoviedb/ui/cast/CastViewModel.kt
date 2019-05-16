package phuchh.sunasterisk.projectmoviedb.ui.cast

import android.arch.lifecycle.Transformations
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class CastViewModel(val repository: MovieRepository):BaseViewModel() {
    fun getCast(movieId:Int) = repository.getCast(movieId)
}
package phuchh.sunasterisk.projectmoviedb.ui.actor

import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class ActorViewModel(val repository: MovieRepository) : BaseViewModel() {
    fun getProfile(id: Int) = repository.getProfile(id)
    fun getMoviesByActor(id: Int, page: Int) = repository.getMoviesByActor(id, page)
}
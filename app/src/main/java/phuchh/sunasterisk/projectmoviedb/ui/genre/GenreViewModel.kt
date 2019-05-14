package phuchh.sunasterisk.projectmoviedb.ui.genre

import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class GenreViewModel (private val repository: MovieRepository):BaseViewModel() {
    val genres = repository.getGenres()
}

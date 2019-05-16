package phuchh.sunasterisk.projectmoviedb.ui.list_movie

import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class ListMovieViewModel(private val repository: MovieRepository) : BaseViewModel() {
    fun getMoviesByGenre(genreId: Int, page: Int) = repository.getMoviesByGenre(genreId, page)
}
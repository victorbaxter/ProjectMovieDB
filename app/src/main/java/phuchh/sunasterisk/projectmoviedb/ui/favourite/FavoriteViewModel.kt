package phuchh.sunasterisk.projectmoviedb.ui.favourite

import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository

class FavoriteViewModel(val repository: MovieRepository):BaseViewModel() {
    val favorite = repository.getAllFavorite()
}
package phuchh.sunasterisk.projectmoviedb.ui.home

import android.arch.lifecycle.LiveData
import phuchh.sunasterisk.projectmoviedb.base.BaseViewModel
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.ApiResponse
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository


class HomeViewModel(repository: MovieRepository) : BaseViewModel() {
    val popularMovies: LiveData<ApiResponse<List<Movie>>> = repository.getPopularMovies()
    val playingMovies: LiveData<ApiResponse<List<Movie>>> = repository.getPlayingMovies()
    val topMovies: LiveData<ApiResponse<List<Movie>>> = repository.getTopMovies()
    val upComingMovies: LiveData<ApiResponse<List<Movie>>> = repository.getUpComingMovies()
    val latestMovies: LiveData<ApiResponse<List<Movie>>> = repository.getMoviesTrendingByDay()
}

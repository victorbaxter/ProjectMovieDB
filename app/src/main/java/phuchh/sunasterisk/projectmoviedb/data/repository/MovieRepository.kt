package phuchh.sunasterisk.projectmoviedb.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import phuchh.sunasterisk.projectmoviedb.data.model.Genre
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.ApiResponse
import phuchh.sunasterisk.projectmoviedb.data.model.response.GenreResponse
import phuchh.sunasterisk.projectmoviedb.data.model.response.MovieResponse
import phuchh.sunasterisk.projectmoviedb.data.source.local.MovieLocalDataSource
import phuchh.sunasterisk.projectmoviedb.data.source.remote.MovieRemoteDataSource
import phuchh.sunasterisk.projectmoviedb.utils.StringUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieRepository private constructor(
    localDataSource: MovieLocalDataSource,
    remoteDataSource: MovieRemoteDataSource
) {

    companion object {
        private var instance: MovieRepository? = null
        fun getInstance(
            remote: MovieRemoteDataSource,
            local: MovieLocalDataSource
        ): MovieRepository {
            if (instance == null) {
                instance =
                    MovieRepository(local, remote)
            }
            return instance!!
        }
    }

    private var remote = remoteDataSource
    private var local = localDataSource

    fun getAllFavorite(): ObservableArrayList<Movie> {
        return local.getAllFavorite()
    }

    fun addFavorite(movie: Movie): Boolean {
        return local.addFavorite(movie)
    }

    fun deleteFavorite(movie: Movie): Boolean {
        return local.deleteFavorite(movie)
    }

    fun isFavorite(movieId: Int): Boolean {
        return local.isFavorite(movieId)
    }

    fun getPopularMovies(): LiveData<ApiResponse<List<Movie>>> = fetchMovies(remote.getPopularMovies())

    fun getPlayingMovies(): LiveData<ApiResponse<List<Movie>>> = fetchMovies(remote.getPlayingMovies())

    fun getTopMovies(): LiveData<ApiResponse<List<Movie>>> = fetchMovies(remote.getTopMovies())

    fun getMoviesTrendingByDay(): LiveData<ApiResponse<List<Movie>>> = fetchMovies(remote.getMoviesTrendingByDay())

    fun getUpComingMovies(): LiveData<ApiResponse<List<Movie>>> = fetchMovies(remote.getComingMovies())

    fun getMovieDetails(id: Int): LiveData<ApiResponse<Movie>> {
        val data = MutableLiveData<ApiResponse<Movie>>()
        remote.getMovieDetails(id).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    data.postValue(ApiResponse(response.body()))
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                data.postValue(ApiResponse(t))
            }
        })
        return data
    }

    fun getGenres(): LiveData<List<Genre>> {
        val data = MutableLiveData<List<Genre>>()
        remote.getGenres().enqueue(object : Callback<GenreResponse> {
            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                if (response.isSuccessful) {
                    data.value = response.body()!!.genres
                }
            }

            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
            }
        })
        return data
    }

    fun getMoviesByGenre(genreId: Int, page: Int): LiveData<ApiResponse<List<Movie>>> =
        fetchMovies(remote.getMoviesByGenre(genreId, page))

    private fun fetchMovies(call: Call<MovieResponse>): LiveData<ApiResponse<List<Movie>>> {
        val data = MutableLiveData<ApiResponse<List<Movie>>>()
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (!response.isSuccessful) {
                    val error = Throwable(StringUtils.parseError(response))
                    data.postValue(ApiResponse(error))
                    return
                }
                if (response.isSuccessful) {
                    data.postValue(ApiResponse(response.body()!!.results))
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                data.postValue(ApiResponse(t))
            }
        })
        return data
    }
}

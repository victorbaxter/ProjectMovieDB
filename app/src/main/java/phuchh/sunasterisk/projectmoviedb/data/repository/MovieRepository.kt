package phuchh.sunasterisk.projectmoviedb.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.MovieResponse
import phuchh.sunasterisk.projectmoviedb.data.source.local.MovieLocalDataSource
import phuchh.sunasterisk.projectmoviedb.data.source.remote.MovieRemoteDataSource
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

    fun getPopularMovies(): LiveData<List<Movie>> = fetchMovies(remote.getPopularMovies())

    fun getPlayingMovies(): LiveData<List<Movie>> = fetchMovies(remote.getPlayingMovies())

    fun getTopMovies(): LiveData<List<Movie>> = fetchMovies(remote.getTopMovies())

    fun getMoviesTrendingByDay(): LiveData<List<Movie>> = fetchMovies(remote.getMoviesTrendingByDay())

    fun getMovieDetails(id: Int): LiveData<Movie> {
        val data = MutableLiveData<Movie>()
        remote.getMovieDetails(id).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    data.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
            }
        })
        return data
    }

    private fun fetchMovies(call: Call<MovieResponse>): LiveData<List<Movie>> {
        val data = MutableLiveData<List<Movie>>()
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    data.value = response.body()!!.results
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            }
        })
        return data
    }
}

package phuchh.sunasterisk.projectmoviedb.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import phuchh.sunasterisk.projectmoviedb.data.model.*
import phuchh.sunasterisk.projectmoviedb.data.model.response.ApiResponse
import phuchh.sunasterisk.projectmoviedb.data.model.response.CreditResponse
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

    fun getAllFavorite(): LiveData<List<Movie>> = local.getAllFavorite()

    fun addFavorite(movie: Movie) = local.addFavorite(movie)

    fun deleteFavorite(movie: Movie) = local.deleteFavorite(movie)

    fun getMovieById(movieId: Int) = local.getMovieById(movieId)

    fun getPopularMovies() = fetchMovies(remote.getPopularMovies())

    fun getPlayingMovies() = fetchMovies(remote.getPlayingMovies())

    fun getTopMovies() = fetchMovies(remote.getTopMovies())

    fun getMoviesTrendingByDay() = fetchMovies(remote.getMoviesTrendingByDay())

    fun getUpComingMovies() = fetchMovies(remote.getComingMovies())

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

    fun getCast(movieId: Int): LiveData<ApiResponse<List<Cast>>> {
        val data = MutableLiveData<ApiResponse<List<Cast>>>()
        remote.getCredits(movieId).enqueue(object : Callback<CreditResponse> {
            override fun onResponse(call: Call<CreditResponse>, response: Response<CreditResponse>) {
                if (response.isSuccessful) {
                    data.postValue(ApiResponse(response.body()!!.cast))
                    return
                }
                val error = Throwable(StringUtils.parseError(response))
                data.postValue(ApiResponse(error))
            }

            override fun onFailure(call: Call<CreditResponse>, t: Throwable) {
                data.postValue(ApiResponse((t)))
            }
        })
        return data
    }

    fun getCrew(movieId: Int): LiveData<ApiResponse<List<Crew>>> {
        val data = MutableLiveData<ApiResponse<List<Crew>>>()
        remote.getCredits(movieId).enqueue(object : Callback<CreditResponse> {
            override fun onResponse(call: Call<CreditResponse>, response: Response<CreditResponse>) {
                if (response.isSuccessful) {
                    data.postValue(ApiResponse(response.body()!!.crew))
                    return
                }
                val error = Throwable(StringUtils.parseError(response))
                data.postValue(ApiResponse(error))
            }

            override fun onFailure(call: Call<CreditResponse>, t: Throwable) {
                data.postValue(ApiResponse((t)))
            }
        })
        return data
    }

    fun getProfile(id: Int): LiveData<ApiResponse<Actor>> {
        val data = MutableLiveData<ApiResponse<Actor>>()
        remote.getProfile(id).enqueue(object : Callback<Actor> {
            override fun onResponse(call: Call<Actor>, response: Response<Actor>) {
                if (response.isSuccessful) {
                    data.postValue(ApiResponse(response.body()))
                    return
                }
                val error = Throwable(StringUtils.parseError(response))
                data.postValue(ApiResponse(error))

            }

            override fun onFailure(call: Call<Actor>, t: Throwable) {
                data.postValue(ApiResponse((t)))
            }
        })
        return data
    }

    fun getMoviesByActor(id: Int, page: Int) = fetchMovies(remote.getMoviesByActor(id, page))

    fun searchMoviesByName(query: String, page: Int) = fetchMovies(remote.searchMovieByName(query, page))

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

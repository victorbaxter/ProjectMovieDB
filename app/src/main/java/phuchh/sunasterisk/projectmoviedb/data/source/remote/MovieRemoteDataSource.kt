package phuchh.sunasterisk.projectmoviedb.data.source.remote

import android.content.Context
import phuchh.sunasterisk.projectmoviedb.data.model.Actor
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.GenreResponse
import phuchh.sunasterisk.projectmoviedb.data.model.response.MovieResponse
import phuchh.sunasterisk.projectmoviedb.data.source.MovieDataSource
import retrofit2.Call

class MovieRemoteDataSource private constructor(private val apiRequest: ApiRequest) : MovieDataSource.Remote {
    companion object {
        private var movieRemoteDataSource: MovieRemoteDataSource? = null

        fun getInstance(context: Context): MovieRemoteDataSource {
            if (movieRemoteDataSource == null) {
                movieRemoteDataSource = MovieRemoteDataSource(NetworkService.getInstance(context))
            }
            return movieRemoteDataSource!!
        }
    }

    override fun getGenres(): Call<GenreResponse> {
        return apiRequest.getGenres()
    }

    override fun getPopularMovies(): Call<MovieResponse> {
        return apiRequest.getPopularMovies()
    }

    override fun getComingMovies(): Call<MovieResponse> {
        return apiRequest.getComingMovies()
    }

    override fun getPlayingMovies(): Call<MovieResponse> {
        return apiRequest.getComingMovies()
    }

    override fun getTopMovies(): Call<MovieResponse> {
        return apiRequest.getTopMovies()
    }
    override fun getMoviesTrendingByDay(): Call<MovieResponse> {
        return apiRequest.getTrendingMoviesByDay()
    }

    override fun getMoviesByCategory(categoryType: String, page: Int): Call<MovieResponse> {
        return apiRequest.getMoviesByCategory(categoryType, page)
    }

    override fun getMoviesByGenre(idGenre: String, page: Int): Call<MovieResponse> {
        return apiRequest.getMoviesByGenre(idGenre, page)
    }

    override fun getMoviesByActor(idActor: String, page: Int): Call<MovieResponse> {
        return apiRequest.getMoviesByActor(idActor, page)
    }

    override fun getMoviesByCompany(idCompany: Int, page: Int): Call<MovieResponse> {
        return apiRequest.getMoviesByCompany(idCompany, page)
    }

    override fun getMovieDetails(idMovie: Int): Call<Movie> {
        return apiRequest.getMovieDetails(idMovie)
    }

    override fun searchMovieByName(key: String, page: Int): Call<MovieResponse> {
        return apiRequest.searchMovieByName(key, page)
    }

    override fun getProfile(actorId: String): Call<Actor> {
        return apiRequest.getProfile(actorId)
    }
}

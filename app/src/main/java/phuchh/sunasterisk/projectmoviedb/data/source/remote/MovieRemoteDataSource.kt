package phuchh.sunasterisk.projectmoviedb.data.source.remote

import android.content.Context
import io.reactivex.Observable
import phuchh.sunasterisk.projectmoviedb.data.model.Actor
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.CreditResponse
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
        return apiRequest.getPlayingMovies()
    }

    override fun getTopMovies(): Call<MovieResponse> {
        return apiRequest.getTopMovies()
    }
    override fun getMoviesTrendingByDay(): Call<MovieResponse> {
        return apiRequest.getTrendingMoviesByDay()
    }

    override fun getMoviesByGenre(genreId:Int, page: Int): Observable<MovieResponse> {
        return apiRequest.getMoviesByGenre(genreId, page)
    }

    override fun getCredits(movieId: Int): Observable<CreditResponse> {
        return apiRequest.getCredits(movieId.toString())
    }

    override fun getMoviesByActor(actorId: Int, page: Int): Observable<MovieResponse> {
        return apiRequest.getMoviesByActor(actorId, page)
    }

    override fun getMovieDetails(movieId: Int): Observable<Movie> {
        return apiRequest.getMovieDetails(movieId.toString())
    }

    override fun searchMovieByName(key: String, page: Int): Observable<MovieResponse> {
        return apiRequest.searchMovieByName(key, page)
    }

    override fun getProfile(actorId: Int): Observable<Actor> {
        return apiRequest.getProfile(actorId.toString())
    }
}

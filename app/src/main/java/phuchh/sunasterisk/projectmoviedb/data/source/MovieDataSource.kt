package phuchh.sunasterisk.projectmoviedb.data.source

import android.arch.lifecycle.LiveData
import io.reactivex.Observable
import phuchh.sunasterisk.projectmoviedb.data.model.Actor
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.CreditResponse
import phuchh.sunasterisk.projectmoviedb.data.model.response.GenreResponse
import phuchh.sunasterisk.projectmoviedb.data.model.response.MovieResponse
import retrofit2.Call

interface MovieDataSource {
    interface Local {
        fun getAllFavorite(): LiveData<List<Movie>>

        fun addFavorite(movie: Movie)

        fun deleteFavorite(movie: Movie)

        fun getMovieById(id: Int): Movie
    }

    interface Remote {
        fun getGenres(): Call<GenreResponse>

        fun getPopularMovies(): Call<MovieResponse>

        fun getPlayingMovies(): Call<MovieResponse>

        fun getTopMovies(): Call<MovieResponse>

        fun getComingMovies(): Call<MovieResponse>

        fun getMoviesTrendingByDay(): Call<MovieResponse>

        fun getMoviesByGenre(genreId: Int, page: Int): Observable<MovieResponse>

        fun getCredits(movieId: Int): Observable<CreditResponse>

        fun getMoviesByActor(actorId: Int, page: Int): Observable<MovieResponse>

        fun getMovieDetails(movieId: Int): Observable<Movie>

        fun searchMovieByName(key: String, page: Int): Observable<MovieResponse>

        fun getProfile(actorId: Int): Observable<Actor>
    }
}

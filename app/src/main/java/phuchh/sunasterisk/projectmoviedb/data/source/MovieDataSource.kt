package phuchh.sunasterisk.projectmoviedb.data.source

import android.databinding.ObservableArrayList
import io.reactivex.Observable
import phuchh.sunasterisk.projectmoviedb.data.model.Actor
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.GenreResponse
import phuchh.sunasterisk.projectmoviedb.data.model.response.MovieResponse

class MovieDataSource {
    interface Local {
        fun getAllFavorite(): ObservableArrayList<Movie>

        fun addFavorite(movie: Movie): Boolean

        fun deleteFavorite(movie: Movie): Boolean

        fun isFavorite(movieId: Int): Boolean
    }

    interface Remote {
        fun getGenres(): Observable<GenreResponse>

        fun getMoviesTrendingByDay(): Observable<MovieResponse>

        fun getMoviesByCategory(categoryType: String, page: Int): Observable<MovieResponse>

        fun getMoviesByGenre(idGenre: String, page: Int): Observable<MovieResponse>

        fun getMoviesByActor(idActor: String, page: Int): Observable<MovieResponse>

        fun getMoviesByCompany(idCompany: Int, page: Int): Observable<MovieResponse>

        fun getMovieDetail(idMovie: Int): Observable<Movie>

        fun searchMovieByName(key: String, page: Int): Observable<MovieResponse>

        fun getProfile(actorId: String): Observable<Actor>
    }
}

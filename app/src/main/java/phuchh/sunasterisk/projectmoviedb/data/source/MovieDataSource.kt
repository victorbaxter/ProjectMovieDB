package phuchh.sunasterisk.projectmoviedb.data.source

import android.databinding.ObservableArrayList
import io.reactivex.Observable
import phuchh.sunasterisk.projectmoviedb.data.model.Actor
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.GenreResponse
import phuchh.sunasterisk.projectmoviedb.data.model.response.MovieResponse
import retrofit2.Call

interface MovieDataSource {
    interface Local {
        fun getAllFavorite(): ObservableArrayList<Movie>

        fun addFavorite(movie: Movie): Boolean

        fun deleteFavorite(movie: Movie): Boolean

        fun isFavorite(movieId: Int): Boolean
    }

    interface Remote {
        fun getGenres(): Call<GenreResponse>

        fun getPopularMovies(): Call<MovieResponse>

        fun getPlayingMovies(): Call<MovieResponse>

        fun getTopMovies(): Call<MovieResponse>

        fun getComingMovies(): Call<MovieResponse>

        fun getMoviesTrendingByDay(): Call<MovieResponse>

        fun getMoviesByCategory(categoryType: String, page: Int): Call<MovieResponse>

        fun getMoviesByGenre(idGenre: String, page: Int): Call<MovieResponse>

        fun getMoviesByActor(idActor: String, page: Int): Call<MovieResponse>

        fun getMoviesByCompany(idCompany: Int, page: Int): Call<MovieResponse>

        fun getMovieDetails(movieId: Int): Call<Movie>

        fun searchMovieByName(key: String, page: Int): Call<MovieResponse>

        fun getProfile(actorId: String): Call<Actor>
    }
}

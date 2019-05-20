package phuchh.sunasterisk.projectmoviedb.data.source

import android.arch.lifecycle.LiveData
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

        fun getMoviesByCategory(categoryType: String, page: Int): Call<MovieResponse>

        fun getMoviesByGenre(genreId: Int, page: Int): Call<MovieResponse>

        fun getCredits(movieId: Int): Call<CreditResponse>

        fun getMoviesByActor(actorId: Int, page: Int): Call<MovieResponse>

        fun getMoviesByCompany(idCompany: Int, page: Int): Call<MovieResponse>

        fun getMovieDetails(movieId: Int): Call<Movie>

        fun searchMovieByName(key: String, page: Int): Call<MovieResponse>

        fun getProfile(actorId: Int): Call<Actor>
    }
}

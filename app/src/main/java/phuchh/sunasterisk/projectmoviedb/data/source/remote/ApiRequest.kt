package phuchh.sunasterisk.projectmoviedb.data.source.remote

import io.reactivex.Observable
import phuchh.sunasterisk.projectmoviedb.data.model.Actor
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.CreditResponse
import phuchh.sunasterisk.projectmoviedb.data.model.response.GenreResponse
import phuchh.sunasterisk.projectmoviedb.data.model.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRequest {
    @GET("genre/movie/list")
    fun getGenres(): Call<GenreResponse>

    @GET("movie/popular")
    fun getPopularMovies(): Call<MovieResponse>

    @GET("movie/now_playing")
    fun getPlayingMovies(): Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopMovies(): Call<MovieResponse>

    @GET("movie/upcoming")
    fun getComingMovies(): Call<MovieResponse>

    @GET("trending/movie/day")
    fun getTrendingMoviesByDay(): Call<MovieResponse>

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Observable<MovieResponse>

    @GET("movie/now_playing")
    fun getPlayingMovies(@Query("page") page: Int): Observable<MovieResponse>

    @GET("movie/top_rated")
    fun getTopMovies(@Query("page") page: Int): Observable<MovieResponse>

    @GET("movie/upcoming")
    fun getComingMovies(@Query("page") page: Int): Observable<MovieResponse>

    @GET("discover/movie")
    fun getMoviesByGenre(
        @Query("with_genres") idGenre: Int,
        @Query("page") page: Int
    ): Observable<MovieResponse>

    @GET("movie/{movie_id}/credits")
    fun getCredits(@Path("movie_id") movieId: String): Observable<CreditResponse>

    @GET("discover/movie")
    fun getMoviesByActor(
        @Query("with_people") id: Int,
        @Query("page") page: Int
    ): Observable<MovieResponse>

    @GET("movie/{movie_id}?append_to_response=videos")
    fun getMovieDetails(@Path("movie_id") id: String): Observable<Movie>

    @GET("search/movie")
    fun searchMovieByName(
        @Query("query") key: String,
        @Query("page") page: Int
    ): Observable<MovieResponse>

    @GET("person/{actor_id}")
    fun getProfile(@Path("actor_id") actorId: String): Observable<Actor>
}

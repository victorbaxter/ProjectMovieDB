package phuchh.sunasterisk.projectmoviedb.data.source.remote

import phuchh.sunasterisk.projectmoviedb.data.model.Actor
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
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

    @GET("movie/{type}")
    fun getMoviesByCategory(
        @Path("type") type: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("discover/movie")
    fun getMoviesByGenre(
        @Query("with_genres") idGenre: Int,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("discover/movie")
    fun getMoviesByActor(
        @Query("with_cast") idCast: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("discover/movie")
    fun getMoviesByCompany(
        @Query("with_companies") idCompany: Int,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("movie/{movie_id}?append_to_response=videos")
    fun getMovieDetails(@Path("movie_id") id: String): Call<Movie>

    @GET("search/movie")
    fun searchMovieByName(
        @Query("query") key: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("person/{actor_id}")
    fun getProfile(@Path("actor_id") actorId: String): Call<Actor>
}

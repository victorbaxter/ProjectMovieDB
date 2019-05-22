package phuchh.sunasterisk.projectmoviedb.data.source.remote

import android.content.Context
import phuchh.sunasterisk.projectmoviedb.data.source.MovieDataSource

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

    override fun getGenres() = apiRequest.getGenres()

    override fun getPopularMovies() = apiRequest.getPopularMovies()

    override fun getComingMovies() = apiRequest.getComingMovies()

    override fun getPlayingMovies() = apiRequest.getPlayingMovies()

    override fun getTopMovies() = apiRequest.getTopMovies()

    override fun getMoviesTrendingByDay() = apiRequest.getTrendingMoviesByDay()

    override fun getPopularMovies(page: Int) = apiRequest.getPopularMovies(page)

    override fun getComingMovies(page: Int) = apiRequest.getComingMovies(page)

    override fun getPlayingMovies(page: Int) = apiRequest.getPlayingMovies(page)

    override fun getTopMovies(page: Int) = apiRequest.getTopMovies(page)

    override fun getMoviesByGenre(genreId: Int, page: Int) = apiRequest.getMoviesByGenre(genreId, page)

    override fun getCredits(movieId: Int) = apiRequest.getCredits(movieId.toString())

    override fun getMoviesByActor(actorId: Int, page: Int) = apiRequest.getMoviesByActor(actorId, page)

    override fun getMovieDetails(movieId: Int) = apiRequest.getMovieDetails(movieId.toString())

    override fun searchMovieByName(key: String, page: Int) = apiRequest.searchMovieByName(key, page)

    override fun getProfile(actorId: Int) = apiRequest.getProfile(actorId.toString())
}

package phuchh.sunasterisk.projectmoviedb.data.source.remote

import android.content.Context
import io.reactivex.Observable
import phuchh.sunasterisk.projectmoviedb.data.model.Actor
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.GenreResponse
import phuchh.sunasterisk.projectmoviedb.data.model.response.MovieResponse
import phuchh.sunasterisk.projectmoviedb.data.source.MovieDataSource

class MovieRemoteDataSource private constructor(apiRequest: ApiRequest) : MovieDataSource.Remote {
    private var mApiRequest = apiRequest

    companion object {
        private var mMovieRemoteDataSource: MovieRemoteDataSource? = null

        fun getInstance(context: Context): MovieRemoteDataSource {
            if (mMovieRemoteDataSource == null) {
                mMovieRemoteDataSource = MovieRemoteDataSource(NetworkService.getInstance(context))
            }
            return mMovieRemoteDataSource!!
        }
    }

    override fun getGenres(): Observable<GenreResponse> {
        return mApiRequest.getGenres()
    }

    override fun getMoviesTrendingByDay(): Observable<MovieResponse> {
        return mApiRequest.getTrendingMoviesByDay()
    }

    override fun getMoviesByCategory(categoryType: String, page: Int): Observable<MovieResponse> {
        return mApiRequest.getMoviesByCategory(categoryType, page)
    }

    override fun getMoviesByGenre(idGenre: String, page: Int): Observable<MovieResponse> {
        return mApiRequest.getMoviesByGenre(idGenre, page)
    }

    override fun getMoviesByActor(idActor: String, page: Int): Observable<MovieResponse> {
        return mApiRequest.getMoviesByActor(idActor, page)
    }

    override fun getMoviesByCompany(idCompany: Int, page: Int): Observable<MovieResponse> {
        return mApiRequest.getMoviesByCompany(idCompany, page)
    }

    override fun getMovieDetail(idMovie: Int): Observable<Movie> {
        return mApiRequest.getMovieDetail(idMovie)
    }

    override fun searchMovieByName(key: String, page: Int): Observable<MovieResponse> {
        return mApiRequest.searchMovieByName(key, page)
    }

    override fun getProfile(actorId: String): Observable<Actor> {
        return mApiRequest.getProfile(actorId)
    }
}

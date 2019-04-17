package phuchh.sunasterisk.projectmoviedb.data.source

import android.databinding.ObservableArrayList
import io.reactivex.Observable
import phuchh.sunasterisk.projectmoviedb.data.model.Actor
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.model.response.GenreResponse
import phuchh.sunasterisk.projectmoviedb.data.model.response.MovieResponse
import phuchh.sunasterisk.projectmoviedb.data.source.local.MovieLocalDataSource
import phuchh.sunasterisk.projectmoviedb.data.source.remote.MovieRemoteDataSource

class MovieRepository private constructor(
    localDataSource: MovieLocalDataSource,
    remoteDataSource: MovieRemoteDataSource
) : MovieDataSource.Local, MovieDataSource.Remote {

    companion object {
        private var sInstance: MovieRepository? = null
        fun getInstance(
            remote: MovieRemoteDataSource,
            local: MovieLocalDataSource
        ): MovieRepository {
            if (sInstance == null) {
                sInstance = MovieRepository(local, remote)
            }
            return sInstance!!
        }
    }

    private var mRemote = remoteDataSource
    private var mLocal = localDataSource

    override fun getAllFavorite(): ObservableArrayList<Movie> {
        return mLocal.getAllFavorite()
    }

    override fun addFavorite(movie: Movie): Boolean {
        return mLocal.addFavorite(movie)
    }

    override fun deleteFavorite(movie: Movie): Boolean {
        return mLocal.deleteFavorite(movie)
    }

    override fun isFavorite(movieId: Int): Boolean {
        return mLocal.isFavorite(movieId)
    }

    override fun getGenres(): Observable<GenreResponse> {
        return mRemote.getGenres()
    }

    override fun getMoviesTrendingByDay(): Observable<MovieResponse> {
        return mRemote.getMoviesTrendingByDay()
    }

    override fun getMoviesByCategory(categoryType: String, page: Int): Observable<MovieResponse> {
        return mRemote.getMoviesByCategory(categoryType, page)
    }

    override fun getMoviesByGenre(idGenre: String, page: Int): Observable<MovieResponse> {
        return mRemote.getMoviesByGenre(idGenre, page)
    }

    override fun getMoviesByActor(idActor: String, page: Int): Observable<MovieResponse> {
        return mRemote.getMoviesByActor(idActor, page)
    }

    override fun getMoviesByCompany(idCompany: Int, page: Int): Observable<MovieResponse> {
        return mRemote.getMoviesByCompany(idCompany, page)
    }

    override fun getMovieDetail(idMovie: Int): Observable<Movie> {
        return mRemote.getMovieDetail(idMovie)
    }

    override fun searchMovieByName(key: String, page: Int): Observable<MovieResponse> {
        return mRemote.searchMovieByName(key, page)
    }

    override fun getProfile(actorId: String): Observable<Actor> {
        return mRemote.getProfile(actorId)
    }
}

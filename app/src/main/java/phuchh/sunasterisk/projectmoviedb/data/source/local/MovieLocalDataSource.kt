package phuchh.sunasterisk.projectmoviedb.data.source.local

import android.arch.lifecycle.LiveData
import android.content.Context
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.source.MovieDataSource

class MovieLocalDataSource(context: Context) : MovieDataSource.Local {
    companion object {
        private var instance: MovieLocalDataSource? = null

        fun getInstance(context: Context): MovieLocalDataSource {
            if (instance == null) instance = MovieLocalDataSource(context)
            return instance!!
        }
    }

    private val db: AppDatabase? = AppDatabase.getInstance(context)
    private val movieDao = db!!.movieDao()

    override fun getAllFavorite(): LiveData<List<Movie>> = movieDao.getAllFavorite()

    override fun addFavorite(movie: Movie) = movieDao.addFavorite(movie)

    override fun deleteFavorite(movie: Movie) = movieDao.deleteFavorite(movie)

    override fun getMovieById(id: Int): Movie = movieDao.getFavoriteById(id)
}
package phuchh.sunasterisk.projectmoviedb.data.source.local

import android.content.Context
import android.databinding.ObservableArrayList
import phuchh.sunasterisk.projectmoviedb.data.model.Movie
import phuchh.sunasterisk.projectmoviedb.data.source.MovieDataSource

class MovieLocalDataSource : MovieDataSource.Local {
    companion object {
        private var sInstance: MovieLocalDataSource? = null

        fun getInstance(): MovieLocalDataSource {
            if (sInstance == null) sInstance = MovieLocalDataSource()
            return sInstance!!
        }
    }


    override fun getAllFavorite(): ObservableArrayList<Movie> {
        //TODO: Update local
        return ObservableArrayList()
    }

    override fun addFavorite(movie: Movie): Boolean {
        return false
    }

    override fun deleteFavorite(movie: Movie): Boolean {
        return false
    }

    override fun isFavorite(movieId: Int): Boolean {
        return false
    }
}
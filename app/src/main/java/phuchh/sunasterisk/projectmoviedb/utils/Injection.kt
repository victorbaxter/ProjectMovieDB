package phuchh.sunasterisk.projectmoviedb.utils

import android.content.Context
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository
import phuchh.sunasterisk.projectmoviedb.data.source.local.MovieLocalDataSource
import phuchh.sunasterisk.projectmoviedb.data.source.remote.MovieRemoteDataSource

object Injection {
    fun provideMovieRepository(context: Context): MovieRepository {
        return MovieRepository.getInstance(MovieRemoteDataSource.getInstance(context), MovieLocalDataSource())
    }
}
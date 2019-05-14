package phuchh.sunasterisk.projectmoviedb.utils

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.annotation.VisibleForTesting
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository
import phuchh.sunasterisk.projectmoviedb.ui.details.DetailsViewModel
import phuchh.sunasterisk.projectmoviedb.ui.genre.GenreViewModel
import phuchh.sunasterisk.projectmoviedb.ui.home.HomeViewModel
import phuchh.sunasterisk.projectmoviedb.ui.main.MainViewModel
import phuchh.sunasterisk.projectmoviedb.ui.movie.MovieDetailsViewModel

class ViewModelFactory private constructor(
    private val movieRepository: MovieRepository
) : ViewModelProvider.NewInstanceFactory() {
    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideMovieRepository(application.applicationContext)
                )
                    .also { INSTANCE = it }
            }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(movieRepository)
                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(movieRepository)
                isAssignableFrom(DetailsViewModel::class.java) ->
                    DetailsViewModel(movieRepository)
                isAssignableFrom(MovieDetailsViewModel::class.java) ->
                    MovieDetailsViewModel(movieRepository)
                isAssignableFrom(GenreViewModel::class.java) ->
                    GenreViewModel(movieRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

}
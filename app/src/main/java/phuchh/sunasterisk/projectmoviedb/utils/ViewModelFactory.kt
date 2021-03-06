package phuchh.sunasterisk.projectmoviedb.utils

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.annotation.VisibleForTesting
import phuchh.sunasterisk.projectmoviedb.data.repository.MovieRepository
import phuchh.sunasterisk.projectmoviedb.ui.actor.ActorViewModel
import phuchh.sunasterisk.projectmoviedb.ui.cast.CastViewModel
import phuchh.sunasterisk.projectmoviedb.ui.details.DetailsViewModel
import phuchh.sunasterisk.projectmoviedb.ui.favourite.FavoriteViewModel
import phuchh.sunasterisk.projectmoviedb.ui.genre.GenreViewModel
import phuchh.sunasterisk.projectmoviedb.ui.home.HomeViewModel
import phuchh.sunasterisk.projectmoviedb.ui.list_movie.ListMovieViewModel
import phuchh.sunasterisk.projectmoviedb.ui.main.MainViewModel
import phuchh.sunasterisk.projectmoviedb.ui.movie.MovieDetailsViewModel
import phuchh.sunasterisk.projectmoviedb.ui.producer.ProducerViewModel
import phuchh.sunasterisk.projectmoviedb.ui.search.SearchViewModel

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
                    MainViewModel()
                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(movieRepository)
                isAssignableFrom(DetailsViewModel::class.java) ->
                    DetailsViewModel(movieRepository)
                isAssignableFrom(MovieDetailsViewModel::class.java) ->
                    MovieDetailsViewModel(movieRepository)
                isAssignableFrom(GenreViewModel::class.java) ->
                    GenreViewModel(movieRepository)
                isAssignableFrom(ListMovieViewModel::class.java) ->
                    ListMovieViewModel(movieRepository)
                isAssignableFrom(CastViewModel::class.java) ->
                    CastViewModel(movieRepository)
                isAssignableFrom(ProducerViewModel::class.java) ->
                    ProducerViewModel(movieRepository)
                isAssignableFrom(ActorViewModel::class.java) ->
                    ActorViewModel(movieRepository)
                isAssignableFrom(SearchViewModel::class.java) ->
                    SearchViewModel(movieRepository)
                isAssignableFrom(FavoriteViewModel::class.java) ->
                    FavoriteViewModel(movieRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

}
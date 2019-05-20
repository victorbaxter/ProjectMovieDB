package phuchh.sunasterisk.projectmoviedb.data.source.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import phuchh.sunasterisk.projectmoviedb.data.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(movie: Movie)

    @Delete
    fun deleteFavorite(movie: Movie)

    @Query("SELECT * FROM movie ORDER BY id DESC")
    fun getAllFavorite(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getFavoriteById(id: Int): Movie
}
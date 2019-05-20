package phuchh.sunasterisk.projectmoviedb.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import phuchh.sunasterisk.projectmoviedb.data.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    companion object {
        var INSTANCE : AppDatabase? = null
        fun getInstance(context: Context):AppDatabase?{
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "movieDB").build()
                }
            }
            return INSTANCE
        }
    }
}
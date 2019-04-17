package phuchh.sunasterisk.projectmoviedb.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import phuchh.sunasterisk.projectmoviedb.data.model.Genre

class GenreResponse {
    @SerializedName("genres")
    @Expose
    private var mGenres: List<Genre>? = null

    fun getGenres(): List<Genre>? {
        return mGenres
    }

    fun setGenres(genres: List<Genre>) {
        mGenres = genres
    }
}

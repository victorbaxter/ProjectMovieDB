package phuchh.sunasterisk.projectmoviedb.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import phuchh.sunasterisk.projectmoviedb.data.model.Genre

class GenreResponse {
    @SerializedName("genres")
    val genres: List<Genre>? = null
}

package phuchh.sunasterisk.projectmoviedb.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
class Movie {
    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("backdrop_path")
    var backdropPath: String? = null

    @Ignore
    @SerializedName("genres")
    val genres: List<Genre>? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("overview")
    var overview: String? = null

    @SerializedName("poster_path")
    var posterPath: String? = null

    @Ignore
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>? = null

    @SerializedName("release_date")
    var releaseDate: String? = null

    @SerializedName("runtime")
    var runtime: Int = 0

    @SerializedName("status")
    var status: String? = null

    @SerializedName("vote_average")
    var voteAverage: Double = 0.toDouble()

    @Ignore
    @SerializedName("videos")
    val videoResult: VideoResult? = null

    inner class VideoResult {
        @SerializedName("results")
        val videos: List<Video>? = null
    }
}

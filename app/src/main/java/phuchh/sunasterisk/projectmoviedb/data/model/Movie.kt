package phuchh.sunasterisk.projectmoviedb.data.model

import com.google.gson.annotations.SerializedName

class Movie {
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("backdrop_path")
    val backdropPath: String? = null

    @SerializedName("genres")
    val genres: List<Genre>? = null

    @SerializedName("title")
    val title: String? = null

    @SerializedName("overview")
    val overview: String? = null

    @SerializedName("poster_path")
    val posterPath: String? = null

    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>? = null

    @SerializedName("release_date")
    val releaseDate: String? = null

    @SerializedName("runtime")
    val runtime: Int = 0

    @SerializedName("status")
    val status: String? = null

    @SerializedName("vote_average")
    val voteAverage: Double = 0.toDouble()

    @SerializedName("videos")
    val videoResult: VideoResult? = null

    inner class VideoResult {
        @SerializedName("results")
        val videos: List<Video>? = null
    }
}

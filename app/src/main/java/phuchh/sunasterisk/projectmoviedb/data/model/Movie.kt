package phuchh.sunasterisk.projectmoviedb.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Movie {
    @SerializedName("id")
    @Expose
    private var mId: Int = 0

    @SerializedName("backdrop_path")
    @Expose
    private var mBackdropPath: String? = null

    @SerializedName("genres")
    @Expose
    private var mGenres: List<Genre>? = null

    @SerializedName("title")
    @Expose
    private var mTitle: String? = null

    @SerializedName("overview")
    @Expose
    private var mOverview: String? = null

    @SerializedName("poster_path")
    @Expose
    private var mPosterPath: String? = null

    @SerializedName("production_companies")
    @Expose
    private var mProductionProductionCompanies: List<ProductionCompany>? = null

    @SerializedName("release_date")
    @Expose
    private var mReleaseDate: String? = null

    @SerializedName("runtime")
    @Expose
    private var mRuntime: Int = 0

    @SerializedName("vote_average")
    @Expose
    private var mVoteAverage: Double = 0.toDouble()

    @SerializedName("videos")
    @Expose
    private var mVideoResult: VideoResult? = null

    @SerializedName("credits")
    @Expose
    private var mCastResult: CastResult? = null

    fun getId(): Int {
        return mId
    }

    fun setId(id: Int) {
        mId = id
    }

    fun getBackdropPath(): String? {
        return mBackdropPath
    }

    fun setBackdropPath(backdropPath: String) {
        mBackdropPath = backdropPath
    }

    fun getGenres(): List<Genre>? {
        return mGenres
    }

    fun setGenres(genres: List<Genre>) {
        mGenres = genres
    }

    fun getTitle(): String? {
        return mTitle
    }

    fun setTitle(title: String) {
        mTitle = title
    }

    fun getOverview(): String? {
        return mOverview
    }

    fun setOverview(overview: String) {
        mOverview = overview
    }

    fun getPosterPath(): String? {
        return mPosterPath
    }

    fun setPosterPath(posterPath: String) {
        mPosterPath = posterPath
    }

    fun getProductionCompanies(): List<ProductionCompany>? {
        return mProductionProductionCompanies
    }

    fun setProductionCompanies(productionProductionCompanies: List<ProductionCompany>) {
        mProductionProductionCompanies = productionProductionCompanies
    }

    fun getReleaseDate(): String? {
        return mReleaseDate
    }

    fun setReleaseDate(releaseDate: String) {
        mReleaseDate = releaseDate
    }

    fun getRuntime(): Int {
        return mRuntime
    }

    fun setRuntime(runtime: Int) {
        mRuntime = runtime
    }

    fun getVoteAverage(): Double {
        return mVoteAverage
    }

    fun setVoteAverage(voteAverage: Double) {
        mVoteAverage = voteAverage
    }

    fun getVideoResult(): VideoResult? {
        return mVideoResult
    }

    fun setVideoResult(videoResult: VideoResult) {
        mVideoResult = videoResult
    }

    fun getCastResult(): CastResult? {
        return mCastResult
    }

    fun setCastResult(castResult: CastResult) {
        mCastResult = castResult
    }

    inner class CastResult {
        @SerializedName("cast")
        @Expose
        var actors: List<Actor>? = null
    }

    inner class VideoResult {
        @SerializedName("results")
        @Expose
        var videos: List<Video>? = null
    }
}

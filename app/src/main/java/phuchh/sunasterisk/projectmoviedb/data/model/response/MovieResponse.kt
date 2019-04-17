package phuchh.sunasterisk.projectmoviedb.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import phuchh.sunasterisk.projectmoviedb.data.model.Movie

class MovieResponse {
    @SerializedName("page")
    @Expose
    private var mPage: Int = 0

    @SerializedName("total_page")
    @Expose
    private var mTotalPage: Int = 0

    @SerializedName("total_results")
    @Expose
    private var mTotalResult: Int = 0

    @SerializedName("results")
    @Expose
    private var mResults: List<Movie>? = null

    fun getPage(): Int {
        return mPage
    }

    fun setPage(page: Int) {
        mPage = page
    }

    fun getTotalPage(): Int {
        return mTotalPage
    }

    fun setTotalPage(totalPage: Int) {
        mTotalPage = totalPage
    }

    fun getTotalResult(): Int {
        return mTotalResult
    }

    fun setTotalResult(totalResult: Int) {
        mTotalResult = totalResult
    }

    fun getResults(): List<Movie>? {
        return mResults
    }

    fun setResults(results: List<Movie>) {
        mResults = results
    }
}

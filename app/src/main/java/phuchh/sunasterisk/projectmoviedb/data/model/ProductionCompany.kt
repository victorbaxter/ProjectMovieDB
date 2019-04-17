package phuchh.sunasterisk.projectmoviedb.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductionCompany {
    @SerializedName("id")
    @Expose
    private var mId: Int = 0

    @SerializedName("name")
    @Expose
    private var mName: String? = null

    @SerializedName("logo_path")
    @Expose
    private var mLogoPath: String? = null

    @SerializedName("origin_country")
    @Expose
    private var mCountry: String? = null

    fun getId(): Int {
        return mId
    }

    fun setId(id: Int) {
        mId = id
    }

    fun getName(): String? {
        return mName
    }

    fun setName(name: String) {
        mName = name
    }

    fun getLogoPath(): String? {
        return mLogoPath
    }

    fun setLogoPath(logoPath: String) {
        mLogoPath = logoPath
    }

    fun getCountry(): String? {
        return mCountry
    }

    fun setCountry(country: String) {
        mCountry = country
    }
}

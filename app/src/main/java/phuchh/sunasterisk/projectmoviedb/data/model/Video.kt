package phuchh.sunasterisk.projectmoviedb.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Video {
    @SerializedName("id")
    @Expose
    private var mId: String? = null
    @SerializedName("iso_639_1")
    @Expose
    private var mIso639: String? = null
    @SerializedName("iso_3166_1")
    @Expose
    private var mIso3166: String? = null
    @SerializedName("key")
    @Expose
    private var mKey: String? = null
    @SerializedName("name")
    @Expose
    private var mName: String? = null
    @SerializedName("site")
    @Expose
    private var mSite: String? = null
    @SerializedName("size")
    @Expose
    private var mSize: Int = 0
    @SerializedName("type")
    @Expose
    private var mType: String? = null

    fun getId(): String? {
        return mId
    }

    fun setId(id: String) {
        mId = id
    }

    fun getIso639(): String? {
        return mIso639
    }

    fun setIso639(iso639: String) {
        mIso639 = iso639
    }

    fun getIso3166(): String? {
        return mIso3166
    }

    fun setIso3166(iso3166: String) {
        mIso3166 = iso3166
    }

    fun getKey(): String? {
        return mKey
    }

    fun setKey(key: String) {
        mKey = key
    }

    fun getName(): String? {
        return mName
    }

    fun setName(name: String) {
        mName = name
    }

    fun getSite(): String? {
        return mSite
    }

    fun setSite(site: String) {
        mSite = site
    }

    fun getSize(): Int {
        return mSize
    }

    fun setSize(size: Int) {
        mSize = size
    }

    fun getType(): String? {
        return mType
    }

    fun setType(type: String) {
        mType = type
    }
}

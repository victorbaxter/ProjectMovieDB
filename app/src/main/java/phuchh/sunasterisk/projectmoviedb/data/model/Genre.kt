package phuchh.sunasterisk.projectmoviedb.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Genre{
    @SerializedName("id")
    @Expose
    private var mId: String? = null
    @SerializedName("name")
    @Expose
    private var mName: String? = null

    fun getId(): String? {
        return mId
    }

    fun setId(id: String) {
        mId = id
    }

    fun getName(): String? {
        return mName
    }

    fun setName(name: String) {
        mName = name
    }
}

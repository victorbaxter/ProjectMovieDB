package phuchh.sunasterisk.projectmoviedb.data.model

import com.google.gson.annotations.SerializedName

class Video {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("iso_639_1")
    var iso639: String? = null

    @SerializedName("iso_3166_1")
    var iso3166: String? = null

    @SerializedName("key")
    var key: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("site")
    var site: String? = null

    @SerializedName("size")
    var size: Int = 0

    @SerializedName("type")
    var type: String? = null
}

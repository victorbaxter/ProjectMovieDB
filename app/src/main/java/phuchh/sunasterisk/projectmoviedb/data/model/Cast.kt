package phuchh.sunasterisk.projectmoviedb.data.model

import com.google.gson.annotations.SerializedName

class Cast {
    @SerializedName("cast_id")
    val castId: Int = 0

    @SerializedName("character")
    val character: String? = null

    @SerializedName("credit_id")
    val creditId: String? = null

    @SerializedName("gender")
    val gender: Int = 0

    @SerializedName("id")
    val id: Int = 0

    @SerializedName("name")
    val name: String? = null

    @SerializedName("order")
    val order: Int = 0

    @SerializedName("profile_path")
    val profilePath: String? = null
}
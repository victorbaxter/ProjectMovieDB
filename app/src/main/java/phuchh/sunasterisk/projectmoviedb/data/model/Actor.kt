package phuchh.sunasterisk.projectmoviedb.data.model

import com.google.gson.annotations.SerializedName

class Actor{
    @SerializedName("id")
    val id: String? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("profile_path")
    val profilePath: String? = null

    @SerializedName("birthday")
    val birthday: String? = null

    @SerializedName("deathday")
    val deathDay: String? = null

    @SerializedName("biography")
    val biography: String? = null

    @SerializedName("place_of_birth")
    val place: String? = null

    @SerializedName("popularity")
    val popularity: String? = null

    @SerializedName("gender")
    val gender: String? = null

    @SerializedName("known_for_department")
    val department: String? = null
}

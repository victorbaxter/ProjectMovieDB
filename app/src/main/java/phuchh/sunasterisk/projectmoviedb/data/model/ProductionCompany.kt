package phuchh.sunasterisk.projectmoviedb.data.model

import com.google.gson.annotations.SerializedName

class ProductionCompany {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("logo_path")
    var logoPath: String? = null

    @SerializedName("origin_country")
    var country: String? = null
}

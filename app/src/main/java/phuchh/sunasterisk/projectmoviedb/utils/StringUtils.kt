package phuchh.sunasterisk.projectmoviedb.utils

import phuchh.sunasterisk.projectmoviedb.data.model.Actor
import phuchh.sunasterisk.projectmoviedb.data.model.Genre
import phuchh.sunasterisk.projectmoviedb.data.model.ProductionCompany
import retrofit2.Response

object StringUtils {
    private const val HYPHEN = " - "
    @JvmStatic
    fun getImage(image_path: String?): String? {
        if (image_path == null) return null
        val builder = StringBuilder()
        builder.append(Constants.BASE_IMAGE_PATH)
            .append(Constants.IMAGE_SIZE_W500)
            .append(image_path)
        return builder.toString()
    }

    @JvmStatic
    fun getProductionCompany(companies: List<ProductionCompany>): String {
        val builder = StringBuilder()
        for (company in companies) {
            builder.append(company.name).append(HYPHEN)
        }
        return builder.toString()
    }

    @JvmStatic
    fun getGenres(genres: List<Genre>): String {
        val builder = StringBuilder()
        for (genre in genres) {
            builder.append(genre.name).append(HYPHEN)
        }
        return builder.toString()
    }

    @JvmStatic
    fun parseError(response: Response<*>): String {
        val builder = StringBuilder()
        builder.append(response.code()).append(HYPHEN).append(response.message())
        return builder.toString()
    }

    @JvmStatic
    fun getActorPOB(actor: Actor): String {
        val builder = StringBuilder()
        builder.append(actor.birthday).append(HYPHEN).append(actor.place)
        return builder.toString()
    }
}
package phuchh.sunasterisk.projectmoviedb.utils

import phuchh.sunasterisk.projectmoviedb.data.model.ProductionCompany

object StringUtils {
    private const val HYPHEN = " - "
    @JvmStatic
    fun getImage(image_path: String): String {
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
}
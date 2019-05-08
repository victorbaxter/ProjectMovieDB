package phuchh.sunasterisk.projectmoviedb.utils

object StringUtils {
    @JvmStatic
    fun getImage(image_path: String): String {
        val builder = StringBuilder()
        builder.append(Constants.BASE_IMAGE_PATH)
            .append(Constants.IMAGE_SIZE_W500)
            .append(image_path)
        return builder.toString()
    }
}
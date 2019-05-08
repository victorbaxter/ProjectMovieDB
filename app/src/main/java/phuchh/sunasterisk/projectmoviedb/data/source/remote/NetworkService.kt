package phuchh.sunasterisk.projectmoviedb.data.source.remote

import android.content.Context

object NetworkService {
    private var apiRequest: ApiRequest? = null

    fun getInstance(context: Context): ApiRequest {
        if (apiRequest == null) {
            apiRequest = RetrofitBuilder.getInstance(context).create(ApiRequest::class.java)
        }
        return apiRequest!!
    }
}

package phuchh.sunasterisk.projectmoviedb.data.source.remote

import android.content.Context

class NetworkService {
    companion object {
        private var mApiRequest: ApiRequest? = null

        fun getInstance(context: Context): ApiRequest {
            if (mApiRequest == null) {
                mApiRequest = RetrofitBuilder.getInstance(context).create(ApiRequest::class.java)
            }
            return mApiRequest!!
        }
    }
}
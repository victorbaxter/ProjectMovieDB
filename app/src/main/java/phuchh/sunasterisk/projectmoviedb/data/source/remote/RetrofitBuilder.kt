package phuchh.sunasterisk.projectmoviedb.data.source.remote

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Cache
import okhttp3.OkHttpClient
import phuchh.sunasterisk.projectmoviedb.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuilder {
    companion object {
        private const val PARAM_API_KEY = "api_key"
        private const val API_KEY = BuildConfig.API_KEY
        private const val CACHE_SIZE = (5 * 1024 * 1024).toLong()
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val READ_TIMEOUT = 5000
        private const val WRITE_TIMEOUT = 5000
        private const val CONNECT_TIMEOUT = 5000
        private const val CACHE_TIME_ONLINE = "public, max-age = 60"// 1 minute
        private const val CACHE_TIME_OFFLINE = "public, only-if-cached, max-stale = 86400"//1 day
        private const val CACHE_CONTROL = "Cache-Control"
        private var mInstance: Retrofit? = null

        fun getInstance(context: Context): Retrofit {
            if (mInstance == null) {
                mInstance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(initClient(context))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return mInstance!!
        }

        private fun initClient(context: Context): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .cache(Cache(context.cacheDir, CACHE_SIZE))
                .addInterceptor { chain ->
                    var request = chain.request()
                    request = if (hasNetwork(context)) {
                        request.newBuilder()
                            .header(CACHE_CONTROL, CACHE_TIME_ONLINE)
                            .build()
                    } else {
                        request.newBuilder()
                            .header(CACHE_CONTROL, CACHE_TIME_OFFLINE)
                            .build()
                    }
                    val requestBuilder = request
                        .newBuilder()
                        .header(PARAM_API_KEY, API_KEY)
                    chain.proceed(requestBuilder.build())
                }
            return builder.build()
        }

        private fun hasNetwork(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}

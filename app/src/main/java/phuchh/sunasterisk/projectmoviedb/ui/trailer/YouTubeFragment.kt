package phuchh.sunasterisk.projectmoviedb.ui.trailer


import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import phuchh.sunasterisk.projectmoviedb.BuildConfig

class YouTubeFragment : YouTubePlayerSupportFragment(), YouTubePlayer.OnInitializedListener {
    private var youTubePlayer: YouTubePlayer? = null
    private var trailerKey: String? = null

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        initialize(BuildConfig.YOUTUBE_API_KEY, this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider,
        youTubePlayer: YouTubePlayer,
        restored: Boolean
    ) {
        this.youTubePlayer = youTubePlayer
        if (!restored && trailerKey != null) {
            this.youTubePlayer!!.fullscreenControlFlags = YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
            this.youTubePlayer!!.cueVideo(trailerKey)
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider,
        youTubeInitializationResult: YouTubeInitializationResult
    ) {
        youTubePlayer = null
        Toast.makeText(context, youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        if (youTubePlayer != null) youTubePlayer!!.release()
        super.onDestroy()
    }

    fun setTrailerKey(trailerKey: String) {
        this.trailerKey = trailerKey
        if (this.trailerKey != null && youTubePlayer != null) youTubePlayer!!.cueVideo(this.trailerKey)
    }

    fun playTrailer() {
        if (youTubePlayer != null) youTubePlayer!!.play()
    }

    fun setFullScreen(isFullScreen: Boolean) = youTubePlayer!!.setFullscreen(isFullScreen)
}

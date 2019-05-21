package phuchh.sunasterisk.projectmoviedb.ui.trailer


import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import phuchh.sunasterisk.projectmoviedb.BuildConfig

class YouTubeFragment : YouTubePlayerSupportFragment(), YouTubePlayer.OnInitializedListener,
    YouTubePlayer.OnFullscreenListener {
    private var youTubePlayer: YouTubePlayer? = null
    private var trailerKey: String? = null
    var isFullscreen = false

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        initialize(BuildConfig.YOUTUBE_API_KEY, this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider,
        player: YouTubePlayer,
        restored: Boolean
    ) {
        this.youTubePlayer = player
        youTubePlayer?.fullscreenControlFlags = YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
        youTubePlayer?.setOnFullscreenListener(this)
        if (!restored && trailerKey != null) {
            youTubePlayer?.cueVideo(trailerKey)
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
        if (youTubePlayer != null) youTubePlayer?.release()
        super.onDestroy()
    }

    override fun onFullscreen(isFullscreen: Boolean) {
        this.isFullscreen = isFullscreen
        if (isFullscreen) {
            enterFullscreen()
            return
        }
        exitFullscreen()
    }

    fun setTrailerKey(trailerKey: String) {
        this.trailerKey = trailerKey
        if (this.trailerKey != null && youTubePlayer != null) {
            youTubePlayer?.cueVideo(this.trailerKey)
        }
    }

    fun release() = youTubePlayer?.release()

    fun exitFullscreen() {
        youTubePlayer?.setFullscreen(false)
        youTubePlayer?.play()

    }

    private fun enterFullscreen() {
        youTubePlayer?.setFullscreen(true)
        youTubePlayer?.play()
    }
}

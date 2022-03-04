package uz.ali.filmtest

import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import uz.ali.filmtest.contents.Contents.YOUTUBE_KEY
import uz.ali.filmtest.databinding.ActivityYoutubeBinding

class YoutubeActivity : YouTubeBaseActivity() {
    lateinit var youTubePlayerViewInit:YouTubePlayer.OnInitializedListener

    private lateinit var binding: ActivityYoutubeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idVideo = intent.getStringExtra("idVideo")


        youTubePlayerViewInit=object :YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo(idVideo)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(applicationContext, "Not Video", Toast.LENGTH_SHORT).show()
            }
        }
        binding.youtubePlayer.initialize(YOUTUBE_KEY,youTubePlayerViewInit)
    }
}
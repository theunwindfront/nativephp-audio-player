package com.nativephp.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import org.json.JSONObject

class AudioFunctions {
    companion object {
        private var mediaPlayer: MediaPlayer? = null

        @JvmStatic
        fun play(context: Context, params: JSONObject): JSONObject {
            val url = params.optString("url")
            val result = JSONObject()

            try {
                mediaPlayer?.stop()
                mediaPlayer?.release()

                mediaPlayer = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                    setDataSource(context, Uri.parse(url))
                    prepare()
                    start()
                }
                result.put("success", true)
            } catch (e: Exception) {
                result.put("success", false)
                result.put("error", e.message)
            }

            return result
        }

        @JvmStatic
        fun pause(context: Context, params: JSONObject): JSONObject {
            mediaPlayer?.pause()
            return JSONObject().put("success", true)
        }

        @JvmStatic
        fun resume(context: Context, params: JSONObject): JSONObject {
            mediaPlayer?.start()
            return JSONObject().put("success", true)
        }

        @JvmStatic
        fun stop(context: Context, params: JSONObject): JSONObject {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            return JSONObject().put("success", true)
        }

        @JvmStatic
        fun seek(context: Context, params: JSONObject): JSONObject {
            val seconds = params.optDouble("seconds", 0.0)
            mediaPlayer?.seekTo((seconds * 1000).toInt())
            return JSONObject().put("success", true)
        }

        @JvmStatic
        fun setVolume(context: Context, params: JSONObject): JSONObject {
            val level = params.optDouble("level", 1.0).toFloat()
            mediaPlayer?.setVolume(level, level)
            return JSONObject().put("success", true)
        }

        @JvmStatic
        fun getDuration(context: Context, params: JSONObject): JSONObject {
            val duration = mediaPlayer?.duration ?: 0
            return JSONObject().put("duration", duration / 1000.0)
        }

        @JvmStatic
        fun getCurrentPosition(context: Context, params: JSONObject): JSONObject {
            val position = mediaPlayer?.currentPosition ?: 0
            return JSONObject().put("position", position / 1000.0)
        }
    }
}

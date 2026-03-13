package com.theunwindfront.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import com.theunwindfront.mobile.bridge.BridgeFunction
import org.json.JSONObject

class AudioFunctions {
    companion object {
        private var mediaPlayer: MediaPlayer? = null

        private fun JSONObject.toMap(): Map<String, Any> {
            val map = mutableMapOf<String, Any>()
            val keys = this.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                map[key] = this.get(key)
            }
            return map
        }
    }

    class Play(private val context: Context) : BridgeFunction {
        override fun execute(parameters: Map<String, Any>): Map<String, Any> {
            val params = JSONObject(parameters)
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

            return result.toMap()
        }
    }

    class Pause(private val context: Context) : BridgeFunction {
        override fun execute(parameters: Map<String, Any>): Map<String, Any> {
            mediaPlayer?.pause()
            return mapOf("success" to true)
        }
    }

    class Resume(private val context: Context) : BridgeFunction {
        override fun execute(parameters: Map<String, Any>): Map<String, Any> {
            mediaPlayer?.start()
            return mapOf("success" to true)
        }
    }

    class Stop(private val context: Context) : BridgeFunction {
        override fun execute(parameters: Map<String, Any>): Map<String, Any> {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            return mapOf("success" to true)
        }
    }

    class Seek(private val context: Context) : BridgeFunction {
        override fun execute(parameters: Map<String, Any>): Map<String, Any> {
            val params = JSONObject(parameters)
            val seconds = params.optDouble("seconds", 0.0)
            mediaPlayer?.seekTo((seconds * 1000).toInt())
            return mapOf("success" to true)
        }
    }

    class SetVolume(private val context: Context) : BridgeFunction {
        override fun execute(parameters: Map<String, Any>): Map<String, Any> {
            val params = JSONObject(parameters)
            val level = params.optDouble("level", 1.0).toFloat()
            mediaPlayer?.setVolume(level, level)
            return mapOf("success" to true)
        }
    }

    class GetDuration(private val context: Context) : BridgeFunction {
        override fun execute(parameters: Map<String, Any>): Map<String, Any> {
            val duration = mediaPlayer?.duration ?: 0
            return mapOf("duration" to duration / 1000.0)
        }
    }

    class GetCurrentPosition(private val context: Context) : BridgeFunction {
        override fun execute(parameters: Map<String, Any>): Map<String, Any> {
            val position = mediaPlayer?.currentPosition ?: 0
            return mapOf("position" to position / 1000.0)
        }
    }
}

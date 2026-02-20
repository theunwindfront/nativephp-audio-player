package com.nativephp.audio

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.nativephp.mobile.bridge.BridgeFunction

class AudioFunctions {
    companion object {
        private const val TAG = "AudioFunctions"
        private var mediaPlayer: MediaPlayer? = null

        @JvmStatic
        fun play(activity: FragmentActivity): BridgeFunction {
            return object : BridgeFunction {
                override fun execute(parameters: Map<String, Any>): Map<String, Any> {
                    val url = parameters["url"] as? String ?: ""
                    Log.d(TAG, "🎵 Playing audio: $url")
                    val result = mutableMapOf<String, Any>()

                    try {
                        mediaPlayer?.stop()
                        mediaPlayer?.release()
                        mediaPlayer = null

                        mediaPlayer = MediaPlayer().apply {
                            setAudioAttributes(
                                AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .build()
                            )

                            if (url.startsWith("asset:///")) {
                                val assetPath = url.substring(9) // remove "asset:///"
                                Log.d(TAG, "📦 Loading from assets: $assetPath")
                                val afd = activity.assets.openFd(assetPath)
                                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                                afd.close()
                            } else {
                                Log.d(TAG, "🌐 Loading from URL: $url")
                                setDataSource(activity, Uri.parse(url))
                            }

                            prepare()
                            start()
                        }
                        Log.d(TAG, "✅ Playback started successfully")
                        result["success"] = true
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ Playback failed: ${e.message}", e)
                        result["success"] = false
                        result["error"] = e.message ?: "Unknown error"
                    }
                    return result
                }
            }
        }

        @JvmStatic
        fun pause(activity: FragmentActivity): BridgeFunction {
            return object : BridgeFunction {
                override fun execute(parameters: Map<String, Any>): Map<String, Any> {
                    mediaPlayer?.pause()
                    return mapOf("success" to true)
                }
            }
        }

        @JvmStatic
        fun resume(activity: FragmentActivity): BridgeFunction {
            return object : BridgeFunction {
                override fun execute(parameters: Map<String, Any>): Map<String, Any> {
                    mediaPlayer?.start()
                    return mapOf("success" to true)
                }
            }
        }

        @JvmStatic
        fun stop(activity: FragmentActivity): BridgeFunction {
            return object : BridgeFunction {
                override fun execute(parameters: Map<String, Any>): Map<String, Any> {
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer = null
                    return mapOf("success" to true)
                }
            }
        }

        @JvmStatic
        fun seek(activity: FragmentActivity): BridgeFunction {
            return object : BridgeFunction {
                override fun execute(parameters: Map<String, Any>): Map<String, Any> {
                    val seconds = (parameters["seconds"] as? Number)?.toDouble() ?: 0.0
                    mediaPlayer?.seekTo((seconds * 1000).toInt())
                    return mapOf("success" to true)
                }
            }
        }

        @JvmStatic
        fun setVolume(activity: FragmentActivity): BridgeFunction {
            return object : BridgeFunction {
                override fun execute(parameters: Map<String, Any>): Map<String, Any> {
                    val level = (parameters["level"] as? Number)?.toFloat() ?: 1.0f
                    mediaPlayer?.setVolume(level, level)
                    return mapOf("success" to true)
                }
            }
        }

        @JvmStatic
        fun getDuration(activity: FragmentActivity): BridgeFunction {
            return object : BridgeFunction {
                override fun execute(parameters: Map<String, Any>): Map<String, Any> {
                    val duration = mediaPlayer?.duration ?: 0
                    return mapOf("duration" to duration / 1000.0)
                }
            }
        }

        @JvmStatic
        fun getCurrentPosition(activity: FragmentActivity): BridgeFunction {
            return object : BridgeFunction {
                override fun execute(parameters: Map<String, Any>): Map<String, Any> {
                    val position = mediaPlayer?.currentPosition ?: 0
                    return mapOf("position" to position / 1000.0)
                }
            }
        }
    }
}

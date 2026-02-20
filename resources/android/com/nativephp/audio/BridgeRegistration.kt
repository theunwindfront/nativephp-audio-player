package com.nativephp.audio

import android.content.Context
import com.nativephp.mobile.bridge.BridgeFunction
import com.nativephp.mobile.bridge.BridgeFunctionRegistry
import org.json.JSONObject

/**
 * Helper to register all Audio bridge functions.
 */
object AudioBridge {
    fun register(registry: BridgeFunctionRegistry, context: Context) {
        registry.register("Audio.play", object : BridgeFunction {
            override fun execute(parameters: Map<String, Any>): Map<String, Any> {
                val result = AudioFunctions.play(context, JSONObject(parameters))
                return result.toMap()
            }
        })

        registry.register("Audio.pause", object : BridgeFunction {
            override fun execute(parameters: Map<String, Any>): Map<String, Any> {
                val result = AudioFunctions.pause(context, JSONObject(parameters))
                return result.toMap()
            }
        })

        registry.register("Audio.resume", object : BridgeFunction {
            override fun execute(parameters: Map<String, Any>): Map<String, Any> {
                val result = AudioFunctions.resume(context, JSONObject(parameters))
                return result.toMap()
            }
        })

        registry.register("Audio.stop", object : BridgeFunction {
            override fun execute(parameters: Map<String, Any>): Map<String, Any> {
                val result = AudioFunctions.stop(context, JSONObject(parameters))
                return result.toMap()
            }
        })

        registry.register("Audio.setVolume", object : BridgeFunction {
            override fun execute(parameters: Map<String, Any>): Map<String, Any> {
                val result = AudioFunctions.setVolume(context, JSONObject(parameters))
                return result.toMap()
            }
        })
    }

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

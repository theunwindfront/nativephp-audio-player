import Foundation
import AVFoundation

@objc(AudioFunctions)
class AudioFunctions: NSObject {
    private static var player: AVPlayer?
    private static var playerItem: AVPlayerItem?

    @objc static func play(_ params: [String: Any]) -> [String: Any] {
        guard let urlString = params["url"] as? String,
              let url = URL(string: urlString) else {
            return ["success": false, "error": "Invalid URL"]
        }

        playerItem = AVPlayerItem(url: url)
        player = AVPlayer(playerItem: playerItem)
        player?.play()

        return ["success": true]
    }

    @objc static func pause(_ params: [String: Any]) -> [String: Any] {
        player?.pause()
        return ["success": true]
    }

    @objc static func resume(_ params: [String: Any]) -> [String: Any] {
        player?.play()
        return ["success": true]
    }

    @objc static func stop(_ params: [String: Any]) -> [String: Any] {
        player?.pause()
        player = nil
        playerItem = nil
        return ["success": true]
    }

    @objc static func seek(_ params: [String: Any]) -> [String: Any] {
        let seconds = params["seconds"] as? Double ?? 0.0
        let time = CMTime(seconds: seconds, preferredTimescale: 600)
        player?.seek(to: time)
        return ["success": true]
    }

    @objc static func setVolume(_ params: [String: Any]) -> [String: Any] {
        let level = params["level"] as? Float ?? 1.0
        player?.volume = level
        return ["success": true]
    }

    @objc static func getDuration(_ params: [String: Any]) -> [String: Any] {
        let duration = playerItem?.duration.seconds ?? 0.0
        return ["duration": duration]
    }

    @objc static func getCurrentPosition(_ params: [String: Any]) -> [String: Any] {
        let position = player?.currentTime().seconds ?? 0.0
        return ["position": position]
    }
}

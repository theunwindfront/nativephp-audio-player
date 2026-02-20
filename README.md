# NativePHP Audio Player

A NativePHP plugin for native audio playback on Android and iOS. Works seamlessly in **both development (Jump Mode) and production (compiled APK/IPA)** — no extra configuration required.

---

## Features

- 🎵 **Play / Pause / Resume / Stop** — Full native playback control
- 🔊 **Volume Control** — Set volume from 0.0 (mute) to 1.0 (max)
- ⏩ **Seek** — Jump to any position in the audio
- ⏱️ **Duration & Position** — Query audio length and current playback position
- 🔄 **Dual-Mode Bridge** — Automatically routes through native bridge in production and browser audio fallback in development
- 🖼️ **`@nativeAudioBridge` Blade directive** — One-line JS relay injection for Livewire apps

---

## Installation

```bash
composer require nativephp/audio-player
```

Then add the `@nativeAudioBridge` directive to your Livewire view (once, at the top):

```blade
@nativeAudioBridge
<div x-data="...">
    ...
</div>
```

> **Note:** `php artisan vendor:publish` is not required. The package auto-discovers via Laravel's package discovery.

---

## Usage

### PHP (Livewire / Controllers)

```php
use Native\Mobile\Audio\Facades\Audio;

// Play an audio file from a URL
Audio::play('https://example.com/audio/music.mp3');

// Pause current playback
Audio::pause();

// Resume paused playback
Audio::resume();

// Stop and reset position
Audio::stop();

// Seek to 30 seconds
Audio::seek(30.0);

// Set volume (0.0 to 1.0)
Audio::setVolume(0.8);

// Get duration of current track
$duration = Audio::getDuration();

// Get current playback position
$position = Audio::getCurrentPosition();
```

---

## How It Works

The package automatically detects the runtime environment and adapts:

| Environment | Audio Engine |
|---|---|
| **Production APK** | `nativephp_call()` → Android `MediaPlayer` / iOS `AVAudioPlayer` |
| **Jump Mode (Dev)** | Livewire dispatches `native-call` → `@nativeAudioBridge` JS relay → Browser `Audio()` API |

### Flow Diagram

```
Audio::play($url)
  ├── nativephp_call() exists? (Production APK)
  │     └── JNI → Native MediaPlayer → 🔊
  │
  └── No nativephp_call (Jump / Dev)
        └── Livewire::dispatch('native-call')
              ↓
        @nativeAudioBridge directive
              ├── window.AndroidBridge.call()? → Native bridge
              └── Fallback → new Audio(url).play() → 🔊
```

---

## API Reference

### PHP Facade: `Native\Mobile\Audio\Facades\Audio`

| Method | Parameters | Returns | Description |
|---|---|---|---|
| `play(string $url)` | URL of the audio file | `bool` | Start playback |
| `pause()` | — | `bool` | Pause playback |
| `resume()` | — | `bool` | Resume paused playback |
| `stop()` | — | `bool` | Stop and reset position |
| `seek(float $seconds)` | Position in seconds | `bool` | Seek to position |
| `setVolume(float $level)` | `0.0` – `1.0` | `bool` | Set volume level |
| `getDuration()` | — | `?float` | Get total audio duration (seconds) |
| `getCurrentPosition()` | — | `?float` | Get current position (seconds) |

### Blade Directive

```blade
@nativeAudioBridge
```

Injects a lightweight `<script>` tag that listens for `native-call` Livewire events and routes them to the appropriate audio engine. Place it once per page, before your Alpine/Livewire component.

---

## Supported Platforms

| Platform | Minimum Version |
|---|---|
| Android | 5.0 (API 21) |
| iOS | 13.0 |

---

## Development vs Production

### Jump Mode (Development)
Run your normal Laravel dev server and the Jump server:
```bash
php artisan serve --host=0.0.0.0 --port=8001
php artisan native:jump --platform=android --ip=YOUR_IP
```

Audio URLs must be reachable from the device (use your machine's LAN IP, not `127.0.0.1`):
```php
Audio::play("http://192.168.1.x:8001/audio/music.mp3");
```

### Production (APK / IPA)
Use any accessible URL or bundle assets:
```php
Audio::play("http://127.0.0.1/audio/music.mp3"); // served from local PHP
```

---

## Changelog

See [RELEASE_NOTES.md](RELEASE_NOTES.md) for a full version history.

---

## License

The MIT License (MIT). Please see the [License File](LICENSE) for more information.

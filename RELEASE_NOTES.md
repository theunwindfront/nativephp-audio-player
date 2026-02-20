# NativePHP Audio Player — Release Notes

---

## v1.0.3 — 2026-02-20

### What's New

**🔄 Dual-Mode Bridge Architecture**

The package now handles audio playback automatically across both development and production environments — no extra setup required.

- **Production (APK/IPA)**: Routes through `nativephp_call()` → native Android `MediaPlayer` / iOS `AVAudioPlayer`
- **Jump Mode (Development)**: Automatically relays via Livewire → JavaScript → Browser `Audio()` API fallback

**🖼️ `@nativeAudioBridge` Blade Directive**

A new, zero-configuration Blade directive that injects the necessary JavaScript relay logic into your view. Drop it once at the top of your Livewire component and the package handles the rest.

```blade
@nativeAudioBridge
<div x-data="gameData()">
    ...
</div>
```

**📦 Centralized `call()` Method**

Refactored all bridge calls to route through a single internal `call()` method in `Audio.php`. This eliminates repetitive boilerplate across all `play()`, `pause()`, `stop()`, `seek()`, and `setVolume()` methods and ensures consistent behavior.

**✅ Zero NativePHP Shell Modifications Required**

The relay mechanism is entirely self-contained within this package. No changes to `MainActivity.kt` or any vendor NativePHP files are needed.

### Changed

- `Audio::play()`, `pause()`, `resume()`, `stop()`, `seek()`, `setVolume()` — all now use centralized `call()` method internally
- `AudioServiceProvider::boot()` — registers the `@nativeAudioBridge` Blade directive
- README fully updated with architecture diagrams, correct namespaces, and development/production instructions

---

## v1.0.2 — 2026-02-20

- Renamed package to `nativephp/audio-player`
- Updated namespace to `Native\Mobile\Audio`

---

## v1.0.1 — 2026-02-20

- Reorganized Android source files
- Added bridge function registration (`AudioBridge`)
- Kotlin `AudioFunctions` class registered with `BridgeFunctionRegistry`

---

## v1.0.0 — 2026-02-20

Initial release.

- Native audio playback for Android and iOS
- Play, pause, resume, stop, seek, volume control
- Duration and position querying
- NativePHP plugin manifest (`nativephp.json`)
- PHP Facade: `Native\Mobile\Audio\Facades\Audio`
<?php

namespace Native\Mobile\Audio;

use Illuminate\Support\ServiceProvider;

class AudioServiceProvider extends ServiceProvider
{
    public function register(): void
    {
        $this->app->singleton(Audio::class, function () {
            return new Audio;
        });
    }

    public function boot(): void
    {
        \Illuminate\Support\Facades\Blade::directive('nativeAudioBridge', function () {
            return <<<'JS'
<script>
(function() {
    // Silent audio state — no UI required
    var _audioUnlocked = false;
    var _pendingPlay = null;
    var _devAudio = null;

    function _playNow(url) {
        if (_devAudio) { _devAudio.pause(); _devAudio = null; }
        _devAudio = new Audio(url);
        _devAudio.play().catch(function() {});
    }

    // Unlock on first user gesture (invisible — no tap indicator needed)
    function _onFirstGesture() {
        _audioUnlocked = true;
        document.body.removeEventListener('click',      _onFirstGesture, true);
        document.body.removeEventListener('touchstart', _onFirstGesture, true);
        if (_pendingPlay) {
            _playNow(_pendingPlay);
            _pendingPlay = null;
        }
    }
    document.body.addEventListener('click',      _onFirstGesture, true);
    document.body.addEventListener('touchstart', _onFirstGesture, true);

    document.addEventListener('livewire:init', function() {
        Livewire.on('native-call', function(event) {
            var data   = event[0];
            var method = data.method;
            var params = data.params;

            // 1. Professional Native Bridge (Production APK / Rebuilt Shell)
            if (window.Native && typeof window.Native.call === 'function') {
                window.Native.call(method, params);
                return;
            }
            if (window.AndroidBridge && window.AndroidBridge.call) {
                window.AndroidBridge.call(method, JSON.stringify(params));
                return;
            }

            // 2. Browser Audio Fallback (Jump Mode development)
            if (method === 'Audio.play' && params && params.url) {
                if (_audioUnlocked) {
                    _playNow(params.url);
                } else {
                    // Queue it — will play on first tap (e.g. pressing Play button)
                    _pendingPlay = params.url;
                }
            } else if (method === 'Audio.stop' || method === 'Audio.pause') {
                _pendingPlay = null;
                if (_devAudio) { _devAudio.pause(); }
            }
        });
    });
})();
</script>
JS;
        });
    }
}

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
document.addEventListener('livewire:init', () => {
    Livewire.on('native-call', (event) => {
        const { method, params } = event[0];
        console.log(`🔌 [Package Relay]: ${method}`, params);
        
        // 1. Try Professional Native Bridge (Production or Rebuilt Shell)
        if (window.Native && typeof window.Native.call === 'function') {
            window.Native.call(method, params);
            return;
        } 
        
        if (window.AndroidBridge && window.AndroidBridge.call) {
            window.AndroidBridge.call(method, JSON.stringify(params));
            return;
        }

        // 2. Development Fallback: Browser Audio (Jump Mode without Shell Rebuild)
        if (method === 'Audio.play' && params.url) {
            console.info('🔈 Bridge not found — Using Browser Audio Fallback');
            if (window._devAudio) window._devAudio.pause();
            window._devAudio = new Audio(params.url);
            window._devAudio.play().catch(e => {
                console.error('❌ Fallback Audio Error:', e);
                if (e.name === 'NotAllowedError') console.warn('📢 Tap screen once to enable audio');
            });
        } else if (method === 'Audio.stop') {
            if (window._devAudio) window._devAudio.pause();
        }
    });
});
</script>
JS;
        });
    }
}

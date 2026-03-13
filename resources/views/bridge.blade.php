<script>
    document.addEventListener('nativephp_event', (event) => {
        const { event: name, payload } = event.detail;

        if (name === 'Theunwindfront\\Audio\\Events\\PlaybackStarted') {
            window.dispatchEvent(new CustomEvent('audio-started', { detail: payload }));
        }

        if (name === 'Theunwindfront\\Audio\\Events\\PlaybackPaused') {
            window.dispatchEvent(new CustomEvent('audio-paused', { detail: payload }));
        }

        if (name === 'Theunwindfront\\Audio\\Events\\PlaybackStopped') {
            window.dispatchEvent(new CustomEvent('audio-stopped', { detail: payload }));
        }

        if (name === 'Theunwindfront\\Audio\\Events\\PlaybackCompleted') {
            window.dispatchEvent(new CustomEvent('audio-completed', { detail: payload }));
        }
    });
</script>

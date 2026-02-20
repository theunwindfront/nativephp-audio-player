<?php

namespace Native\Mobile\Audio;

class Audio
{
    /**
     * Play an audio file from a URL or local path
     */
    public function play(string $url): bool
    {
        return $this->call('Audio.play', ['url' => $url]);
    }

    /**
     * Pause the current audio playback
     */
    public function pause(): bool
    {
        return $this->call('Audio.pause');
    }

    /**
     * Resume the paused audio playback
     */
    public function resume(): bool
    {
        return $this->call('Audio.resume');
    }

    /**
     * Stop the audio playback and reset the position
     */
    public function stop(): bool
    {
        return $this->call('Audio.stop');
    }

    /**
     * Seek to a specific position in the audio (in seconds)
     */
    public function seek(float $seconds): bool
    {
        return $this->call('Audio.seek', ['seconds' => $seconds]);
    }

    /**
     * Set the audio volume
     *
     * @param  float  $level  Volume level from 0.0 (mute) to 1.0 (maximum)
     */
    public function setVolume(float $level): bool
    {
        $level = max(0.0, min(1.0, $level));
        return $this->call('Audio.setVolume', ['level' => $level]);
    }

    /**
     * Get the duration of the current audio in seconds
     */
    public function getDuration(): ?float
    {
        if (function_exists('nativephp_call')) {
            $result = nativephp_call('Audio.getDuration', '{}');

            if ($result) {
                $decoded = json_decode($result);

                return isset($decoded->duration) ? (float) $decoded->duration : null;
            }
        }

        return null;
    }

    /**
     * Get the current playback position in seconds
     */
    public function getCurrentPosition(): ?float
    {
        if (function_exists('nativephp_call')) {
            $result = nativephp_call('Audio.getCurrentPosition', '{}');

            if ($result) {
                $decoded = json_decode($result);

                return isset($decoded->position) ? (float) $decoded->position : null;
            }
        }

        return null;
    }

    /**
     * Execute a bridge call or relay to the frontend in development mode
     */
    protected function call(string $method, array $params = []): bool
    {
        // 1. Production Mode: Native bridge call (works when code is in APK)
        if (function_exists('nativephp_call')) {
            $result = nativephp_call($method, json_encode($params));

            if ($result) {
                $decoded = json_decode($result);
                return $decoded->success ?? false;
            }
            return false;
        }

        // 2. Jump Mode Relay: Dispatch to Livewire frontend
        if (class_exists(\Livewire\Livewire::class)) {
            \Livewire\Livewire::dispatch('native-call', [
                'method' => $method,
                'params' => $params
            ]);
        }

        return true;
    }
}

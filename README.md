# NativePHP Audio Player Plugin

A NativePHP plugin for audio playback on mobile devices.

## Features

- **Play/Pause/Resume** - Full control over audio playback
- **Stop** - Stop and reset playback position
- **Seek** - Jump to any position in the audio
- **Volume Control** - Set volume programmatically
- **Duration/Position** - Get audio duration and current position

## Installation

```bash
# Install the package
composer require nativephp/audio-player

# Publish the plugins provider (first time only)
php artisan vendor:publish --tag=nativephp-plugins-provider

# Register the plugin
php artisan native:plugin:register nativephp/audio-player

# Verify registration
php artisan native:plugin:list
```

## Usage

### PHP (Livewire/Blade)

```php
use NativePHP\Audio\Facades\Audio;

// Play an audio file (using a sample open-source link for testing)
Audio::play('https://www.w3schools.com/html/horse.mp3');

// Pause
Audio::pause();

// Resume
Audio::resume();

// Seek to 30 seconds
Audio::seek(30.0);

// Set volume (0.0 to 1.0)
Audio::setVolume(0.8);

// Get info
$duration = Audio::getDuration();
$position = Audio::getCurrentPosition();
```

### JavaScript (Vue/React/Inertia)

```javascript
import { audioPlayer } from '@nativephp/audio-player';

// Play an audio file (using a sample open-source link for testing)
await audioPlayer.play('https://www.w3schools.com/html/horse.mp3');

// Pause
await audioPlayer.pause();

// Set volume
await audioPlayer.setVolume(1.0);
```

## API Reference

| Method | Returns | Description |
|--------|---------|-------------|
| `play(string $url)` | `bool` | Play an audio file |
| `pause()` | `bool` | Pause playback |
| `resume()` | `bool` | Resume playback |
| `stop()` | `bool` | Stop playback |
| `seek(float $seconds)` | `bool` | Seek to position |
| `setVolume(float $level)` | `bool` | Set volume (0.0-1.0) |
| `getDuration()` | `?float` | Get audio duration |
| `getCurrentPosition()` | `?float` | Get current position |

## Version Support

| Platform | Minimum Version |
|----------|----------------|
| Android  | 5.0 (API 21)   |
| iOS      | 13.0            |

## License

The MIT License (MIT). Please see [License File](LICENSE) for more information.

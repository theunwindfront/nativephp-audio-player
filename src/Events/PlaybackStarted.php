<?php

namespace NativePHP\Audio\Events;

use Illuminate\Foundation\Events\Dispatchable;
use Illuminate\Queue\SerializesModels;

class PlaybackStarted
{
    use Dispatchable, SerializesModels;

    public function __construct(public string $url)
    {
    }
}

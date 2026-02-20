<?php

namespace NativePHP\Audio\Events;

use Illuminate\Foundation\Events\Dispatchable;
use Illuminate\Queue\SerializesModels;

class PlaybackStopped
{
    use Dispatchable, SerializesModels;

    public function __construct()
    {
    }
}

<?php

namespace NativePHP\Audio\Events;

use Illuminate\Foundation\Events\Dispatchable;
use Illuminate\Queue\SerializesModels;

class PlaybackCompleted
{
    use Dispatchable, SerializesModels;

    public function __construct()
    {
    }
}

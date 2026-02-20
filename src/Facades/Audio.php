<?php

namespace Native\Mobile\Audio\Facades;

use Illuminate\Support\Facades\Facade;

/**
 * @method static bool play(string $url)
 * @method static bool pause()
 * @method static bool resume()
 * @method static bool stop()
 * @method static bool seek(float $seconds)
 * @method static bool setVolume(float $level)
 * @method static float|null getDuration()
 * @method static float|null getCurrentPosition()
 *
 * @see \Native\Mobile\Audio\Audio
 */
class Audio extends Facade
{
    protected static function getFacadeAccessor(): string
    {
        return \Native\Mobile\Audio\Audio::class;
    }
}

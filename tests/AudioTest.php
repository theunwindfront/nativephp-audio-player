<?php

namespace Theunwindfront\Audio\Tests;

use Theunwindfront\Audio\Audio;
use Theunwindfront\Audio\Facades\Audio as AudioFacade;
use Theunwindfront\Audio\AudioServiceProvider;
use Orchestra\Testbench\TestCase;

class AudioTest extends TestCase
{
    protected function getPackageProviders($app)
    {
        return [
            AudioServiceProvider::class,
        ];
    }

    protected function getPackageAliases($app)
    {
        return [
            'Audio' => AudioFacade::class,
        ];
    }

    public function test_audio_facade_can_be_resolved()
    {
        $this->assertInstanceOf(Audio::class, $this->app->make(Audio::class));
    }

    public function test_play_returns_false_when_native_call_not_available()
    {
        $audio = new Audio();
        $this->assertFalse($audio->play('https://www.w3schools.com/html/horse.mp3'));
    }
}

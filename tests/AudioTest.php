<?php

namespace NativePHP\Audio\Tests;

use NativePHP\Audio\Audio;
use NativePHP\Audio\Facades\Audio as AudioFacade;
use NativePHP\Audio\AudioServiceProvider;
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

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
        //
    }
}

const audioPlayer = {
    play: async (url) => {
        return await window.nativephp.call('Audio.play', { url });
    },
    pause: async () => {
        return await window.nativephp.call('Audio.pause');
    },
    resume: async () => {
        return await window.nativephp.call('Audio.resume');
    },
    stop: async () => {
        return await window.nativephp.call('Audio.stop');
    },
    seek: async (seconds) => {
        return await window.nativephp.call('Audio.seek', { seconds });
    },
    setVolume: async (level) => {
        return await window.nativephp.call('Audio.setVolume', { level });
    },
    getDuration: async () => {
        return await window.nativephp.call('Audio.getDuration');
    },
    getCurrentPosition: async () => {
        return await window.nativephp.call('Audio.getCurrentPosition');
    }
};

export default audioPlayer;

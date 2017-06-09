package com.lngwu.demo.fireworks.view;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseIntArray;

class SoundPlay {
    static final int SOUND_ID_UP = 0;
    static final int SOUND_ID_BLAST = 1;


    // 音效的音量
    private int streamVolume;

    // 定义SoundPool 对象
    private SoundPool soundPool;

    // 定义HASH表
    private SparseIntArray soundPoolMap;

    /**
     * 初始化声音系统
     *
     * @param context
     */
    void init(Context context) {
        // 初始化soundPool 对象,第一个参数是允许有多少个声音流同时播放,第2个参数是声音类型,第三个参数是声音的品质
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 100);

        // 初始化HASH表
        soundPoolMap = new SparseIntArray();

        // 获得声音设备和设备音量
        AudioManager mgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    /**
     * 加载音效资源，把资源中的音效加载到指定的ID(播放的时候就对应到这个ID播放就行了)
     *
     * @param context
     * @param raw
     * @param ID
     */
    void load(Context context, int raw, int ID) {
        soundPoolMap.put(ID, soundPool.load(context, raw, 1));
    }

    /**
     * 播放声音
     *
     * @param sound 要播放的音效的ID
     * @param loop  循环次数
     */
    public void play(int sound, int loop) {
        soundPool.play(soundPoolMap.get(sound), streamVolume, streamVolume, 1, loop, 1f);
    }
}

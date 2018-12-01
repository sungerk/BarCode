package com.example.sunger.ml;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.util.ArrayMap;

import java.util.Map;

public class SoundManager {

    private SoundPool soundPool;
    Map<Integer, Integer> sounddata;
    private int currentVolume;
    private AudioManager audioManager;
    private boolean needRestVolume = false;


    public SoundManager(Context context, int... raws) {
        sounddata = new ArrayMap<>();
        soundPool = SoundPoolFactory.create();
        for (int i = 0; i < raws.length; i++) {
            int resId = soundPool.load(context, raws[i], 1);
            sounddata.put(i, resId);
        }
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
       // setMaxVolume();
    }

    private void setMaxVolume() {
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        if (currentVolume != maxVolume) {
            needRestVolume = true;
            setVolume(maxVolume);
        }
    }

    private void setVolume(int value) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, value, 0);
    }

    /**
     * 播放音乐
     *
     * @param index 第几个
     */
    public synchronized void play(int index) {
        int currentResId = sounddata.get(index);
        if (currentResId != 0) {
            soundPool.play(currentResId, 1, 1, 0, 0, 1);
        }

    }

    /**
     * 释放资源
     */
    public void release() {
        soundPool.release();
        if (needRestVolume) {
            setVolume(currentVolume);
        }
    }
}

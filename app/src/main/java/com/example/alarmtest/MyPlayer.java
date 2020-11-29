package com.example.alarmtest;

import android.media.MediaPlayer;

public class MyPlayer {
    private static MediaPlayer mp;
    private static MyPlayer single_instance = null;

    // private constructor restricted to this class itself
    private MyPlayer() { }

    // static method to create instance of Singleton class
    public static MyPlayer getInstance()
    {
        if (single_instance == null)
            single_instance = new MyPlayer();

        return single_instance;
    }

    public static void playSound(){
        mp = MediaPlayer.create(MyApplication.getInstance(), R.raw.water);
        mp.setLooping(true);
        mp.start();
    }

    public static void stopSound(){
        if(mp.isPlaying()){
            mp.stop();
        }
    }
}

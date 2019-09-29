package com.tdj_sj_webandroid.utils;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

public class PlayVoice {
    private static MediaPlayer mediaPlayer;

    public static void playVoice(Context context,int res){
        if(mediaPlayer==null){
            mediaPlayer=MediaPlayer.create(context,res);
        }else{
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=MediaPlayer.create(context,res);
        }
            mediaPlayer.start();


    }
    //停止播放声音
    public  static void stopVoice(){
        if(null!=mediaPlayer) {
            mediaPlayer.stop();
        }
    }
}

package com.example.serviceboundmusic;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {

    private MyPlayer myPlayer;
    private IBinder binder;
    private int songPlay;

    public void setSongPlay(int songPlay) {
        this.songPlay = songPlay;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ServiceDemo", "Đã gọi onCreate()");
        binder = new MyBinder(); // do MyBinder được extends Binder

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");
        myPlayer = new MyPlayer(this, songPlay);
        myPlayer.play(this,R.raw.mamaboy);
        // trả về đối tượng binder cho ActivityMain
        return binder;

    }
    // Kết thúc một Service
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");
        myPlayer.stop();
        return super.onUnbind(intent);
    }

    public void setSong(int song){
        myPlayer = new MyPlayer(this, songPlay);
        binder = new MyBinder(); // do MyBinder được extends Binder
    }

    // Xây dựng các phương thức thực hiện nhiệm vụ,
    // ở đây mình demo phương thức tua bài hát
    public  void playSongWant(int Song){
        myPlayer.play(this,Song);
    }


    public void fastForward(int minute){

        myPlayer.fastForward(minute); // tua đến giây thứ 120
    }
    public void fastStart(){

        myPlayer.fastStart();
    }

    public class MyBinder extends Binder {

        // phương thức này trả về đối tượng MyService
        public MyService getService() {

            return MyService.this;
        }
    }

}
// Xây dựng một đối tượng riêng để chơi nhạc
class MyPlayer {
    // đối tượng này giúp phát một bài nhạc
    private MediaPlayer mediaPlayer;


    public MyPlayer(Context context,int idSong) {
        // Nạp bài nhạc vào mediaPlayer
        mediaPlayer = MediaPlayer.create(
                context, R.raw.buonvuongmauao_nguyenhung);
        // Đặt chế độ phát lặp lại liên tục
        mediaPlayer.setLooping(true);
    }





    public void fastForward(int pos){
        //mediaPlayer.seekTo(pos);
        mediaPlayer.pause();

    }
    public void fastStart(){
        mediaPlayer.start();
    }

    // phát nhạc
    public void play(Context context,int idSong) {
//        if (mediaPlayer != null) {
//            mediaPlayer.start();
//        }

        mediaPlayer = MediaPlayer.create(
                context,idSong);
        // Đặt chế độ phát lặp lại liên tục
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    // dừng phát nhạc
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}

package com.example.serviceboundmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.serviceboundmusic.MyService.MyBinder;
import com.example.serviceboundmusic.adapter.SongAdapter;
import com.example.serviceboundmusic.model.Song;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyService myService;
    private boolean isBound = false;
    private ServiceConnection connection;
    private List<Song> songList;
    RecyclerView recyclerView;
    SongAdapter songAdapter;
    EditText editText;
    int play = R.raw.buonvuongmauao_nguyenhung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songList = new ArrayList<>();
        Song song = new Song(R.raw.buonvuongmauao_nguyenhung,"buong vuong");
        Song song1 = new Song(R.raw.mamaboy,"mama boy");
        Song song2 = new Song(R.raw.trentinhbanduoitinhyeu,"tren tình bạn dưới tình yêu");
        songList.add(song);
        songList.add(song1);
        songList.add(song2);


        final Button btOn = (Button) findViewById(R.id.btOn);
        final Button btOff = (Button) findViewById(R.id.btOff);
        final Button btFast = (Button) findViewById(R.id.btTua);
        recyclerView = findViewById(R.id.recycleview);
        editText = findViewById(R.id.minute);
        songAdapter = new SongAdapter(songList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(songAdapter);
        songAdapter.notifyDataSetChanged();

        // Khởi tạo ServiceConnection
        connection = new ServiceConnection() {
            // Phương thức này được hệ thống gọi khi kết nối tới service bị lỗi
            @Override
            public void onServiceDisconnected(ComponentName name) {

                isBound = false;
            }

            // Phương thức này được hệ thống gọi khi kết nối tới service thành công
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyBinder binder = (MyBinder) service;
                myService = binder.getService(); // lấy đối tượng MyService
                isBound = true;
            }
        };

        // Khởi tạo intent
        final Intent intent =
                new Intent(MainActivity.this,
                MyService.class);

        btOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Bắt đầu một service sủ dụng bind
                //myService.setSongPlay(R.raw.mamaboy);
                bindService(intent, connection,
                        Context.BIND_AUTO_CREATE);

                Toast.makeText(MainActivity.this,
                        "Đã chạy "+ R.raw.mamaboy, Toast.LENGTH_SHORT).show();
                // Đối thứ ba báo rằng Service sẽ tự động khởi tạo
            }
        });

        btOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Nếu Service đang hoạt động
                if(isBound){
                    // Tắt Service
                    unbindService(connection);
                    isBound = false;
                }
                Toast.makeText(MainActivity.this,
                        "Đã tắt", Toast.LENGTH_SHORT).show();
            }
        });

        btFast.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // nếu service đang hoạt động
                if(isBound){
                    // tua bài hát
                    String phut = editText.getText().toString();
                    int mi = Integer.parseInt(phut);
                    myService.fastForward(mi);
                }else{
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBound){
                    // tua bài hát
                    myService.fastStart();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Song newSong = songList.get(position);
                        Toast.makeText(MainActivity.this,
                                "đã bấm bài nhạc "+ newSong.getTen(), Toast.LENGTH_SHORT).show();
                        play = newSong.getId();
                        connection = new ServiceConnection() {
                            // Phương thức này được hệ thống gọi khi kết nối tới service bị lỗi
                            @Override
                            public void onServiceDisconnected(ComponentName name) {

                                isBound = false;
                            }

                            // Phương thức này được hệ thống gọi khi kết nối tới service thành công
                            @Override
                            public void onServiceConnected(ComponentName name, IBinder service) {
                                MyBinder binder = (MyBinder) service;
                                myService = binder.getService(); // lấy đối tượng MyService
                                myService.setSongPlay(play);
                                myService.playSongWant(play);
                                isBound = true;
                            }
                        };
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }
}
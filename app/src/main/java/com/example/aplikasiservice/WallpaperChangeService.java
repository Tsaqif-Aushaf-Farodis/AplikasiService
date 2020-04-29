package com.example.aplikasiservice;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class WallpaperChangeService extends Service implements Runnable {
    /*referensi gambar wallpaper disimpan dalam sebuah array,
    wallpaper1 dan wallpaper2 adalah nama filenya.*/
    private int wallpaperId[] = {R.drawable.wallpaper1, R.drawable.wallpaper2, R.drawable.wallpaper3};
    private int waktu;

    /*Deklarasi variabel flag untuk mengecek gambar mana yang akan
    ditampilkan berikutnya.*/
    private int FLAG=0;
    private Thread thread;

    /*Deklarasi 2 variabel bitmat untuk menyimpan gambar wallpaper*/
    private Bitmap gambar1;
    private Bitmap gambar2;
    private Bitmap gambar3;

    @Override
    public IBinder onBind(Intent intent) {
        //TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        super.onStartCommand(intent, flag, startId);
        //mendapatkan bundle yang dikirim lewat intent dari MainActivity
        Bundle bundle = intent.getExtras();
        //baca nilai yang tersimpan dengan kunci "durasi"
        waktu = bundle.getInt("durasi");
        //inisialisasi objek bitmap dengan gambar wallpaper
        gambar1 = BitmapFactory.decodeResource(getResources(),wallpaperId[0]);
        gambar2 = BitmapFactory.decodeResource(getResources(),wallpaperId[1]);
        gambar3 = BitmapFactory.decodeResource(getResources(),wallpaperId[2]);

        //Mulai thread agar service tetap berjalan di latar belakang
        thread = new Thread(WallpaperChangeService.this);
        thread.start();
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }

    /*method run() yang berisi kode yang dieksekusi oleh thread
    yang baru saja dibuat*/
    @Override
    public void run() {
        //TODO Auto-generated method stub
        WallpaperManager myWallpaper;
        myWallpaper = WallpaperManager.getInstance(this);
        try{
            while (true){
                if(FLAG==0){
                    myWallpaper.setBitmap(gambar1);
                    FLAG++;
                }
                else if(FLAG==1){
                    myWallpaper.setBitmap(gambar2);
                    FLAG++;
                }
                else{
                    myWallpaper.setBitmap(gambar3);
                    FLAG--;
                }
                Thread.sleep(100*waktu);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

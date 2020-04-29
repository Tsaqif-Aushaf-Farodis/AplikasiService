package com.example.aplikasiservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    private Button btn_set, btn_unset;
    private RadioButton rb_1mnt, rb_5mnt, rb_30mnt, rb_1jam;
    private RadioGroup radioGroup;
    public int changeTime = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_set = findViewById(R.id.btnSet);
        btn_unset = findViewById(R.id.btnUnset);
        rb_1mnt = findViewById(R.id.radio1);
        rb_5mnt = findViewById(R.id.radio2);
        rb_30mnt = findViewById(R.id.radio3);
        rb_1jam = findViewById(R.id.radio4);
        radioGroup = findViewById(R.id.radioGroup);

        btn_unset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent disable = new Intent(MainActivity.this, WallpaperChangeService.class);
                stopService(disable);
                finish();
            }
        });

        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioID = radioGroup.getCheckedRadioButtonId();
                if(rb_1mnt.getId()==radioID){
                    changeTime=60;
                }
                else if(rb_5mnt.getId()==radioID){
                    changeTime=5*60;
                }
                else if(rb_30mnt.getId()==radioID){
                    changeTime=30*60;
                }
                else if(rb_1jam.getId()==radioID){
                    changeTime=60*60;
                }

                Intent service = new Intent(MainActivity.this, WallpaperChangeService.class);

                //membuat bundle dan menyimpan pasangan nilai dengan kuncinya
                Bundle bundleTime = new Bundle();
                bundleTime.putInt("durasi", changeTime);

                //menaruh bundle kedalam intent
                service.putExtras(bundleTime);
                //memulai service
                startService(service);
                //mengakhiri activity
                finish();
            }
        });
    }
}

package com.ldy.dbassist.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ldy.dbassist.R;
import com.ldy.dbassist.sample.db.DBService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBService dbService = DBService.getInstance();
                dbService.init(1011);
            }
        }).start();
    }
}

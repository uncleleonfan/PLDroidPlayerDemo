package com.leon.pldroidplayerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartPLVideoView(View view) {
        Intent intent = new Intent(this, PLVideoViewActivity.class);
        startActivity(intent);
    }

    public void onStartPLTextureView(View view) {
        Intent intent = new Intent(this, PLVideoTextureActivity.class);
        startActivity(intent);
    }

    public void onStartCustomView(View view) {
        Intent intent = new Intent(this, CustomVideoViewActivity.class);
        startActivity(intent);
    }
}

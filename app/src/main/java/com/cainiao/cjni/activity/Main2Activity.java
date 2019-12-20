package com.cainiao.cjni.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cainiao.cjni.R;
import com.cainiao.cjni.opengl.OpenGlView;

public class Main2Activity extends AppCompatActivity {

    private OpenGlView openGlview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        openGlview = findViewById(R.id.openGlview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        openGlview.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        openGlview.onPause();
    }
}

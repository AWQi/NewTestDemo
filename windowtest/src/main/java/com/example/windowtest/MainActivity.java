package com.example.windowtest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.opengl.Visibility;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setVisible(false);
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SYSTEM_ALERT_WINDOW)==PackageManager.PERMISSION_GRANTED){
            addView();
        }else {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},0);
        }
//        setContentView(R.layout.activity_main);
    }

    void  addView(){
        TextView text = new TextView(MainActivity.this);
        text.setText("hello world !");
        text.setTextSize(30);
        text.setLinkTextColor(Color.WHITE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT,0,0,PixelFormat.TRANSPARENT);
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        layoutParams.gravity = Gravity.CENTER;
        WindowManager windowManager  = getWindowManager();
        windowManager.addView(text,layoutParams);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            switch (requestCode){
                case 0:
                    addView();
                    break;
            }
//        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}

package com.example.sharedesktoptest;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
private Button   btn = null;
private RecorderService recorderService = null;
private  MediaProjectionManager projectionManager;
private ServiceConnection  connection ;
private  Intent serviceIntent = null;
private  Button text ;
private SurfaceView surfaceView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SYSTEM_ALERT_WINDOW)
                        ==PackageManager.PERMISSION_GRANTED
                  &&ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        ==PackageManager.PERMISSION_GRANTED
                  &&ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.RECORD_AUDIO)
                        ==PackageManager.PERMISSION_GRANTED      ){
                    addView();
                }else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.RECORD_AUDIO},0);
                }

            }
        });


    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        this.setVisible(false);
//        setContentView(R.layout.activity_main);
//    }

    void  addView(){
        text = new Button(MainActivity.this);
        text.setText("停止 !");
        text.setTextSize(40);
        text.setLinkTextColor(Color.WHITE);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorderService.stop();
                getWindowManager().removeView(text);
                getWindowManager().removeView(surfaceView);
            }
        });


        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT,0,0,PixelFormat.TRANSPARENT);
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;//  不会拦截事件
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;

        layoutParams.gravity = Gravity.CENTER;
        WindowManager windowManager  = getWindowManager();
        windowManager.addView(text,layoutParams);

        surfaceView = new SurfaceView(MainActivity.this);
        surfaceView.setBackgroundColor(Color.WHITE);
        layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,200,0,0,PixelFormat.TRANSPARENT);
        layoutParams.gravity = Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;//  不会拦截事件
        windowManager.addView(surfaceView,layoutParams);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
        switch (requestCode){
            case 0:
                projectionManager = (MediaProjectionManager) MainActivity.this.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
                startActivityForResult(projectionManager.createScreenCaptureIntent(), 102);
                break;
        }
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


       if (requestCode==102){

           /**
            *   准备工作中 有异步过程  需要  延时保证加载 完成
            */
           Handler handler  = new Handler();
           handler.postDelayed(new RecorderRunnable(resultCode,data),3000);
//           connection = new ServiceConnection() {
//               @Override
//               public void onServiceConnected(ComponentName name, IBinder service) {
//                   Log.d(TAG, "onServiceConnected: ");
//               }
//
//               @Override
//               public void onServiceDisconnected(ComponentName name) {
//                   Log.d(TAG, "onServiceDisconnected: ");
//               }
//           };
//
//           serviceIntent = new Intent(MainActivity.this,RecorderService.class);
//           MainActivity.this.startService(serviceIntent);
//           MainActivity.this.bindService(serviceIntent,connection,Context.BIND_AUTO_CREATE);
       }


    }

    public  class  RecorderRunnable implements  Runnable{
        private  Intent data ;
        private int resultCode;
        public RecorderRunnable(int resultCode,Intent data) {
            this.data = data;
            this.resultCode = resultCode;
        }

        @Override
        public void run() {
            addView();

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            int displayWidth = dm.widthPixels;
            int displayHeight =  dm.heightPixels;
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String fileStr = dir.toString()+"/recorder.mp4";
            File file = new File(fileStr); //录屏生成文件 mediaRecord = new MediaRecordService(displayWidth, displayHeight, 6000000, 1, mediaProjection, file.getAbsolutePath()); mediaRecord.start();

            MediaProjection  projection= projectionManager.getMediaProjection(resultCode,data);
            recorderService = new RecorderService(displayWidth, displayHeight, 6000000, 1,file.getAbsolutePath(), projection,surfaceView);
            recorderService.start();
        }
    }

}

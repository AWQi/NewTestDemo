package com.example.sharedesktoptest;

import android.Manifest;
import android.app.AppOpsManager;
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
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
private Button   btn = null;
private RecorderService recorderService = null;
private  MediaProjectionManager projectionManager;
private ServiceConnection  connection ;
private  Intent serviceIntent = null;
private  Button text ;
private MySurfaceView surfaceView ;
private EditText editText = null;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        btn = findViewById(R.id.btn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(
////                        ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SYSTEM_ALERT_WINDOW)
////                        ==PackageManager.PERMISSION_GRANTED
////                  &&
//                ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        ==PackageManager.PERMISSION_GRANTED
//                  &&ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.RECORD_AUDIO)
//                        ==PackageManager.PERMISSION_GRANTED
//                       ){
//                    requestAndroidOverlayPermission();
//                }else {
//                    ActivityCompat.requestPermissions(MainActivity.this,
//                            new String[]{
////                            Manifest.permission.SYSTEM_ALERT_WINDOW,
//                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                                    Manifest.permission.RECORD_AUDIO
//                            },0);
//                }
//
//            }
//        });
//
//
//    }
//
//
//
//    private void requestAndroidOverlayPermission() {
//
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (!Settings.canDrawOverlays(MainActivity.this)
////                                    ||!(ContextCompat.checkSelfPermission(MainActivity.this,
////                                            Manifest.permission.SYSTEM_ALERT_WINDOW) ==PackageManager.PERMISSION_GRANTED)
//                    ) {
//                Intent intent = new Intent(
////                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
////                        Settings.ACTION_SETTINGS,
//                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, 1);
//            } else {
//                startRecorder();
//            }
//        }
//    }
//
//    private void requestVivoOverlayPermission() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (!Settings.canDrawOverlays(MainActivity.this)
////                                    ||!(ContextCompat.checkSelfPermission(MainActivity.this,
////                                            Manifest.permission.SYSTEM_ALERT_WINDOW) ==PackageManager.PERMISSION_GRANTED)
//                    ) {
//                Intent intent = new Intent(
////                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
////                        Settings.ACTION_SETTINGS,
//                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                        Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, 2);
//            } else {
//
//                 startRecorder();
//            }
//        }
//    }
//
////    /**
////     * 判断 悬浮窗口权限是否打开
////     * @param context
////     * @return true 允许  false禁止
////     */ public boolean checkAlertWindowsPermission(Context context) {
////         try {
////             Object object = context.getSystemService(Context.APP_OPS_SERVICE);
////             if (object == null) {
////                 return false;
////             }
////             Class localClass = object.getClass();
////             Class[] arrayOfClass = new Class[3];
////             arrayOfClass[0] = Integer.TYPE;
////             arrayOfClass[1] = Integer.TYPE;
////             arrayOfClass[2] = String.class;
////             Metchod method = localClass.getMethod("checkOp", arrayOfClass);
////             if (method == null) {
////                 return false;
////             }
////             Object[] arrayOfObject1 = new Object[3];
////             arrayOfObject1[0] = 24;
////             arrayOfObject1[1] = Binder.getCallingUid();
////             arrayOfObject1[2] = context.getPackageName();
////             int m = ((Integer) method.invoke(object, arrayOfObject1));
////             return m == AppOpsManager.MODE_ALLOWED;
////         } catch (Exception ex) { }
////         return false;
////     }
//
//
//
////
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        this.setVisible(false);
////        setContentView(R.layout.activity_main);
////    }
//
//    void  addView(){
//        text = new Button(MainActivity.this);
//        text.setText("停止 !");
//        text.setTextSize(40);
//        text.setLinkTextColor(Color.WHITE);
//        text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recorderService.stop();
//                getWindowManager().removeView(text);
//                getWindowManager().removeView(surfaceView);
//            }
//        });
//
//
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT,0,0,PixelFormat.TRANSPARENT);
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
////        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;//  不会拦截事件
//
//
//        /**
//         *   这里不同的 type  需要用不同的  权限来 添加
//         */
////        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;//
//        /**
//         *   Android 8  权限不同  做适配
//         */
////        layoutParams.type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
//
//
//        layoutParams.type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE;
//
//        layoutParams.gravity = Gravity.CENTER;
//        WindowManager windowManager  = getWindowManager();
//        windowManager.addView(text,layoutParams);
//
//        surfaceView = new MySurfaceView(MainActivity.this);
//        surfaceView.setBackgroundColor(Color.BLACK);
//        layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,200,0,0,PixelFormat.TRANSPARENT);
//        layoutParams.gravity = Gravity.TOP;
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;//  不会拦截事件
//        windowManager.addView(surfaceView,layoutParams);
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
////        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//        switch (requestCode){
//            case 0:
//              requestAndroidOverlayPermission();
//                break;
//        }
////        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode==1&&resultCode==0){
//            requestVivoOverlayPermission();
//        }
//        if (requestCode==2&&resultCode==0){
//                startRecorder();
//        }
//
//       if (requestCode==102){
//           Handler handler  = new Handler();
//           handler.postDelayed(new RecorderRunnable(resultCode,data),3000);
//
////            if (resultCode!=0){
////                Intent intent = new Intent(
//////                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//////                        Settings.ACTION_SETTINGS,
////                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
////                        Uri.parse("package:" + getPackageName()));
////                startActivityForResult(intent, REQUEST_OVERLAY);
////            }else {
////                /**
////                 *   准备工作中 有异步过程  需要  延时保证加载 完成
////                 */
////                Handler handler  = new Handler();
////                handler.postDelayed(new RecorderRunnable(resultCode,data),3000);
////            }
//
//
//
////           connection = new ServiceConnection() {
////               @Override
////               public void onServiceConnected(ComponentName name, IBinder service) {
////                   Log.d(TAG, "onServiceConnected: ");
////               }
////
////               @Override
////               public void onServiceDisconnected(ComponentName name) {
////                   Log.d(TAG, "onServiceDisconnected: ");
////               }
////           };
////
////           serviceIntent = new Intent(MainActivity.this,RecorderService.class);
////           MainActivity.this.startService(serviceIntent);
////           MainActivity.this.bindService(serviceIntent,connection,Context.BIND_AUTO_CREATE);
//       }
//
//
//    }
//
//    public  void  startRecorder(){
//        projectionManager = (MediaProjectionManager) MainActivity.this.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
//        startActivityForResult(projectionManager.createScreenCaptureIntent(), 102);
//    }
//    public  class  RecorderRunnable implements  Runnable{
//        private  Intent data ;
//        private int resultCode;
//        public RecorderRunnable(int resultCode,Intent data) {
//            this.data = data;
//            this.resultCode = resultCode;
//        }
//
//        @Override
//        public void run() {
//            addView();
//
//            DisplayMetrics dm = new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//            int displayWidth = dm.widthPixels;
//            int displayHeight =  dm.heightPixels;
//            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//            String fileStr = dir.toString()+"/recorder.mp4";
//            File file = new File(fileStr); //录屏生成文件 mediaRecord = new MediaRecordService(displayWidth, displayHeight, 6000000, 1, mediaProjection, file.getAbsolutePath()); mediaRecord.start();
//
//            MediaProjection  projection= projectionManager.getMediaProjection(resultCode,data);
//            recorderService = new RecorderService(displayWidth, displayHeight, 6000000, 1,file.getAbsolutePath(), projection,surfaceView);
//            recorderService.start();
//        }
//    }



        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnAndroid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              requestAndroidOverlayPermission();
            }
        });
         findViewById(R.id.btnVivo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestVivoOverlayPermission();
                }
            });
         findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 addView();
             }
         });
         findViewById(R.id.btnRecorder).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                    recoder();
             }
         });
         findViewById(R.id.btnI).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                    i();
             }
         });
         findViewById(R.id.btnCheck).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 check();
             }
         });
         findViewById(R.id.btnPermission).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                permission();
             }
         });
         editText = findViewById(R.id.editText);
         findViewById(R.id.deskTop).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                        MainActivity.this.moveTaskToBack(true);
                        Intent intent = new Intent(MainActivity.this,DeskTopActivity.class);
                        MainActivity.this.startActivity(intent);
                 Log.d(TAG, "跳转DeskTop: ");
             }
         });
    }
        public void  permission(){

                            if(
                        ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SYSTEM_ALERT_WINDOW)
                        ==PackageManager.PERMISSION_GRANTED
                       ){
                  showToast("有权限",this);
                                Log.d(TAG, "有权限: ");
                }else {
                                showToast("无权限",this);
                                Log.d(TAG, "无权限: ");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{
                            Manifest.permission.SYSTEM_ALERT_WINDOW,
                            },0);
                }
        }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "请求结果: "+grantResults[0]);
        Log.d(TAG, "是否有权限: "+
                (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SYSTEM_ALERT_WINDOW)
                ==PackageManager.PERMISSION_GRANTED));
        check();
    }

    public  void  check(){
           if (Settings.canDrawOverlays(MainActivity.this)){
               showToast("true",MainActivity.this);
               Log.d(TAG, "canDrawOverlays: "+"true");
           }else {
               showToast("false",MainActivity.this);
               Log.d(TAG, "canDrawOverlays: "+"false");
           }
           if (Settings.canDrawOverlays(MainActivity.this.getApplicationContext())){
               showToast("true",MainActivity.this);
               Log.d(TAG, "canDrawOverlays: "+"true");
           }else {
               showToast("false",MainActivity.this);
               Log.d(TAG, "canDrawOverlays: "+"false");
           }
        }
        public  void i(){
            check();
            Intent appIntent = this.getPackageManager().getLaunchIntentForPackage("com.iqoo.secure");
            this.startActivityForResult(appIntent,111);

        }
        public  void recoder(){
            check();
         projectionManager = (MediaProjectionManager) MainActivity.this.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(projectionManager.createScreenCaptureIntent(), 102);
    }

        void  addView(){
        text = new Button(MainActivity.this);
        text.setText("停止 !");
        text.setTextSize(40);
        text.setLinkTextColor(Color.WHITE);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT,0,0,PixelFormat.TRANSPARENT);
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;//  不会拦截事件


        /**
         *   这里不同的 type  需要用不同的  权限来 添加
         */
//        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;//
        /**
         *   Android 8  权限不同  做适配
         */
//        layoutParams.type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;


        layoutParams.type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE;

        layoutParams.gravity = Gravity.CENTER;
        WindowManager windowManager  = getWindowManager();
//        windowManager.addView(,layoutParams);
        windowManager.addView(text,layoutParams);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "resultCode: "+resultCode);
        if (projectionManager!=null&&requestCode==-1){
            projectionManager.getMediaProjection(resultCode,data).stop();
            projectionManager=null;
        }
        check();


//        if (requestCode ==2){
//            requestAndroidOverlayPermission();
//        }

//        try {
//            Log.d(TAG, "开始延时: ");
//            Thread.sleep(Long.parseLong(editText.getText().toString()));
//            Log.d(TAG, "结束延时: ");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        check();


//        if (Settings.canDrawOverlays(this)){
//            showToast("true",this);
//            Log.d(TAG, "canDrawOverlays: "+"true");
//        }else {
//            showToast("false",this);
//            Log.d(TAG, "canDrawOverlays: "+"false");
//        }
//        if (Settings.canDrawOverlays(getApplicationContext())){
//            showToast("true",this);
//            Log.d(TAG, "canDrawOverlays: "+"true");
//        }else {
//            showToast("false",this);
//            Log.d(TAG, "canDrawOverlays: "+"false");
//        }

    }
    public  void  showToast(String string ,Context context){
        Toast.makeText(context,string,Toast.LENGTH_SHORT).show();
    }
    private void requestAndroidOverlayPermission() {

//        if (Build.VERSION.SDK_INT >= 23) {
//            if (!Settings.canDrawOverlays(MainActivity.this)
////                                    ||!(ContextCompat.checkSelfPermission(MainActivity.this,
////                                            Manifest.permission.SYSTEM_ALERT_WINDOW) ==PackageManager.PERMISSION_GRANTED)
//                    ) {
        check();
                Intent intent = new Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1);
//            } else {
//                Log.d(TAG, "canDrawOverlays: "+"true");
//            }
//        }
    }

    private void requestVivoOverlayPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
//            if (!Settings.canDrawOverlays(MainActivity.this)
////                                    ||!(ContextCompat.checkSelfPermission(MainActivity.this,
////                                            Manifest.permission.SYSTEM_ALERT_WINDOW) ==PackageManager.PERMISSION_GRANTED)
//                    ) {
            check();
                Intent intent = new Intent(
//                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Settings.ACTION_SETTINGS,
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 2);

//            } else {
//
//            }
        }
    }

}

package com.example.admin.testdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        PermissionPageUtils.getInstance(this).jumpPermissionPage();

//        C:\Users\dong.li> adb shell dumpsys activity top
//        TASK com.iqoo.secure id=164
//        ACTIVITY com.vivo.permissionmanager/.activity.FloatWindowManagerActivity 1f2f62e pid=3234



//        Intent intent = new Intent();
//        ComponentName cn = new ComponentName("com.iqoo.secure", ".activity.SoftPermissionDetailActivity");
//        intent.setAction(Intent.ACTION_MAIN);
//        intent.setComponent(cn);
//        intent.putExtra("aaa","lalla");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);




//        Intent appIntent = this.getPackageManager().getLaunchIntentForPackage("com.iqoo.secure");
//        Intent appIntent = this.getPackageManager().getLaunchIntentForPackage("activity.SoftPermissionDetailActivity");
//
//
//        //        startSafely(context,appIntent);
//        if(appIntent != null){
//            this.startActivity(appIntent);
////            floatingView = new SettingFloatingView(this, "SETTING", getApplication(), 0);
////            floatingView.createFloatingView();
////            floatingView = new SettingFloatingView(this, "SETTING", context.getApplication(), 0);
////            floatingView.createFloatingView();
////            return;
//        }else {
//            Toast.makeText(this,"无法跳转",Toast.LENGTH_LONG).show();
//        }

    }
}

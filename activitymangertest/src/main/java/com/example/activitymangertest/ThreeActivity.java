package com.example.activitymangertest;

import android.app.ActivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class ThreeActivity extends AppCompatActivity {
    private static final String TAG = "ThreeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        ActivityManager am = ActivityManagerUtil.getInstanse(ThreeActivity.this);

        /**
         *
         *         moveTaskToBack(true);
         *   j将当前activity栈移到后台
         *   参数为 false 时 只有当前activity 为根activity时有效
         *   参数为 ture 时 ，任何有一个activity都有效
         */
        moveTaskToBack(true);


//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            List<ActivityManager.AppTask> appTasks = am.getAppTasks();
//            Log.d(TAG, "appTasks: "+appTasks.size());
//             ActivityManager.AppTask task0 = appTasks.get(0);
//            ActivityManager.RecentTaskInfo recentTaskInfo = task0.getTaskInfo();
//                task0.finishAndRemoveTask();
//                task0.moveToFront();
//        }
//        this.finish();
    }
}

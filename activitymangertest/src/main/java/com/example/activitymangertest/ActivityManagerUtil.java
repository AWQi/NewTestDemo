package com.example.activitymangertest;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

public class ActivityManagerUtil {
    static  public ActivityManager am ;
    private Context context ;
    private  ActivityManagerUtil() {
    }
    static  ActivityManager getInstanse(Context context){

//        if (null==am)
        am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return  am;
    }
}

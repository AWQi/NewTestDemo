package com.example.toasttest;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

//public class MainActivity extends AppCompatActivity {
//private TextView tv  ;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
////        MyToast.getIntance(this,"不消失的Toast")
////                .setClickable()
////                .show();
//        tv =  findViewById(R.id.tv);
////        View v = new View(this);
//        Snackbar.make(tv,"Hello SnackBar!", Snackbar.LENGTH_INDEFINITE).show();
////    ClickToast.showToast(this, Toast.LENGTH_LONG);
//
//
//
//        Toast toast=Toast.makeText(MainActivity.this, "这是可以随意设置时间的Toast", Toast.LENGTH_LONG);
//        showMyToast(toast, 90*1000);
//    }
//
//
//
//    public void showMyToast(final Toast toast, final int cnt) {
//        final Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                toast.show();
//            } }, 0, 3500);
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                toast.cancel(); timer.cancel();
//            } }, cnt ); }
//
//}




public class MainActivity extends AppCompatActivity {
    /** HandlerThread object */
    HandlerThread mThread = null;

    /** Handler object */
    ToastHandler mHandler = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mThread = new HandlerThread("ToastThread");
        mThread.start();
        mHandler = new ToastHandler(mThread.getLooper());
        mHandler.sendEmptyMessageDelayed(1, 2000);

    }

    /**
     * Called when Button got clicked
     * @param v View that got clicked
     */
    public void myClick(View v) {
        mHandler.sendEmptyMessageDelayed(1, 2000);
    }

    private class ToastHandler extends Handler {
        public ToastHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast toast = Toast.makeText(MainActivity.this, "Dismiss me", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(1);
        mThread.getLooper().quit();
        mHandler = null;
        mThread = null;
    }
}

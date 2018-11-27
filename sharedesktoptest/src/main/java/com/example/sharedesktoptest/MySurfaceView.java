package com.example.sharedesktoptest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView  extends SurfaceView implements SurfaceHolder.Callback,Runnable{
    private static final String TAG = "MySurfaceView";


    private  SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private  boolean mIsDrawing;
    public MySurfaceView(Context context) {
        super(context);
        initView();
    }

    private  void   initView(){
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated: ");
//        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged: ");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed: ");
        mIsDrawing =false;
    }

    @Override
    public void run() {
            while(mIsDrawing){
             draw();
            }
    }
    private void draw(){
        try {
            canvas = surfaceHolder.lockCanvas();

        } finally {
            if (canvas!=null){
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

    }

    public   void draw(Bitmap bitmap){
        try {
            canvas = surfaceHolder.lockCanvas();

        } finally {
            if (canvas!=null){
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

    }
}

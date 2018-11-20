package com.example.sharedesktoptest;

import android.app.Service;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceView;

import java.io.IOException;

import static android.hardware.display.DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC;

public class RecorderService  {
    private static final String TAG = "RecorderService";
   private  int mWidth;
   private  int mHeight;
   private  int mBitRate;
   private  int mDip;
   private  String mDsPath;
   private MediaRecorder mMediaRecorder;
   private SurfaceView surfaceView;
   private MediaProjection mMediaProjection;
   private  static  final  int FRAME_RATE = 60;

    private VirtualDisplay mVirtualDisplay;

    public RecorderService() {
    }

    public RecorderService(int mWidth, int mHeight, int mBitRate, int mDip, String mDsPath, MediaProjection mMediaProjectioin, SurfaceView surfaceView) {
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.mBitRate = mBitRate;
        this.mDip = mDip;
        this.mDsPath = mDsPath;
        this.mMediaProjection = mMediaProjectioin;
        this.surfaceView = surfaceView;
    }


    public void start() {
        initRecorder();
        // 在 mMediaRecorder.prepare() 后调用
        mVirtualDisplay = mMediaProjection.createVirtualDisplay(TAG,mWidth,mHeight,mDip,VIRTUAL_DISPLAY_FLAG_PUBLIC,mMediaRecorder.getSurface(),null,null);
//        mVirtualDisplay = mMediaProjection.createVirtualDisplay(TAG,mWidth,mHeight,mDip,VIRTUAL_DISPLAY_FLAG_PUBLIC,surfaceView.getHolder().getSurface(),null,null);
        mMediaRecorder.start();
        Log.d(TAG, " start: ");
    }
    public  void  stop(){
/**
 *IllegalStateException这个异常它是指“非法的状态”。
 *
 *         android的MediaRecorder 和MediaPlayer API中用到了JNI，也就是我们的java代码是要调用native的C++方法的
 *         （MediaRecorder ，MediaPlayer 是用c++实现的），
 *
 *         出现这个异常，就是因为我们java里面的MediaRecorder ，MediaPlayer 对象的状态和native的对象状态发生了不一致。
 */


        if (mMediaRecorder != null) {
            mMediaRecorder.setOnErrorListener(null);
            mMediaRecorder.setOnInfoListener(null);
            mMediaRecorder.setPreviewDisplay(null);
            try {
                mMediaRecorder.stop();
            } catch (IllegalStateException e) {
                // TODO 如果当前java状态和jni里面的状态不一致，
                //e.printStackTrace();
                mMediaRecorder = null;
                mMediaRecorder = new MediaRecorder();
            }
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
     if (mMediaProjection!=null)mMediaProjection.stop();
        release();
        Log.d(TAG, "stop: ");
    }



    public void initRecorder(){
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mMediaRecorder.setOutputFile(mDsPath);
        mMediaRecorder.setVideoSize(mWidth,mHeight);
        mMediaRecorder.setVideoFrameRate(FRAME_RATE);
        mMediaRecorder.setVideoEncodingBitRate(mBitRate);
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

       try {
           mMediaRecorder.prepare();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
    public  void  release(){
if (mVirtualDisplay!=null){mVirtualDisplay.release();mVirtualDisplay=null;}
if (mMediaRecorder!=null){mMediaRecorder.release();mMediaRecorder=null;}
if (mMediaProjection!=null){mMediaProjection.stop();mMediaProjection=null;}
       Log.d(TAG, "release: ");
   }

}

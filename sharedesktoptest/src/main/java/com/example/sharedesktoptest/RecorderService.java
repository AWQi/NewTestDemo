package com.example.sharedesktoptest;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

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
   private ImageReader imageReader;
   private MySurfaceView surfaceView;
   private Surface surface;
   private MediaProjection mMediaProjection;
   private  static  final  int FRAME_RATE = 60;

    private VirtualDisplay mVirtualDisplay;

    public RecorderService() {
    }

    public RecorderService(int mWidth, int mHeight, int mBitRate, int mDip, String mDsPath, MediaProjection mMediaProjectioin, final MySurfaceView surfaceView) {
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.mBitRate = mBitRate;
        this.mDip = mDip;
        this.mDsPath = mDsPath;
        this.mMediaProjection = mMediaProjectioin;
        this.surfaceView = surfaceView;
//        this.surface = new Surface();
        imageReader = ImageReader.newInstance(mWidth,mHeight,PixelFormat.RGBA_8888,5);
        imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
//                  Image image =   reader.acquireNextImage();
                  Image image = reader.acquireLatestImage();
                  byte[] bytes = ImageUtil.getRgbaDataFromImage(image);

                  Bitmap bt = ImageUtil.bytes2Bitmap(bytes);
                  surfaceView.draw(bt);
                  image.close();
            }
        },null);

    }


    public void start() {
        initRecorder();
        // 在 mMediaRecorder.prepare() 后调用
//        Surface surface = surfaceView.getHolder().getSurface();
        Surface  surface = mMediaRecorder.getSurface();
//        Surface surface = imageReader.getSurface();
//        mMediaRecorder.setInputSurface(surface);
//        mVirtualDisplay = mMediaProjection.createVirtualDisplay(TAG,mWidth,mHeight,mDip,VIRTUAL_DISPLAY_FLAG_PUBLIC,mMediaRecorder.getSurface(),null,null);
        mVirtualDisplay = mMediaProjection.createVirtualDisplay(TAG,mWidth,mHeight,mDip,VIRTUAL_DISPLAY_FLAG_PUBLIC,surface,null,null);
        mMediaRecorder.start();
        Log.d(TAG, " start: ");
    }
    public  void  stop(){

        if (mMediaRecorder != null) {
            mMediaRecorder.setOnErrorListener(null);
            mMediaRecorder.setOnInfoListener(null);
            mMediaRecorder.setPreviewDisplay(null);
            try {
                mMediaRecorder.stop();
            } catch (IllegalStateException e) {
                e.printStackTrace();
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

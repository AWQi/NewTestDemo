package com.example.bigimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private ImageView mImageView;
    private LargeImageView largeImageView;
    private  OprateLargeImage mOprateLargeImage;
    InputStream inputStream = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView =  findViewById(R.id.mImageView);
//        largeImageView =  findViewById(R.id.id_largetImageview);
 //       mOprateLargeImage = findViewById(R.id.mOprateLargeImage);
        findViewById(R.id.part).setOnClickListener(this);
        findViewById(R.id.src).setOnClickListener(this);
        findViewById(R.id.s).setOnClickListener(this);
        findViewById(R.id.m).setOnClickListener(this);
        findViewById(R.id.l).setOnClickListener(this);



        initImageView();
//        initLargetImageView();
   //     initOprateLargetImageView();

    }

    public  void  initOprateLargetImageView(){
        try {
            inputStream = getAssets().open("a.jpg");
//           Bitmap bitmap = loadPartIamge(inputStream);
            Bitmap bitmap = loadSampleImage(inputStream,0);


//            Bitmap bitmap = loadCompressImage(inputStream,mImageView.getMeasuredWidth(),mImageView.getMeasuredHeight());
            Log.d(TAG, "bitmap.getByteCount():"+    bitmap.getByteCount());

            mOprateLargeImage.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public  void  initLargetImageView(){
        try {
            inputStream = getAssets().open("a.jpg");
            largeImageView.setInputStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public  void  initImageView(){
        try {
            inputStream = getAssets().open("a.jpg");
//           Bitmap bitmap = loadPartIamge(inputStream);
            Bitmap bitmap = loadSampleImage(inputStream,0);


//            Bitmap bitmap = loadCompressImage(inputStream,mImageView.getMeasuredWidth(),mImageView.getMeasuredHeight());
            Log.d(TAG, "bitmap.getByteCount():"+    bitmap.getByteCount());

            mImageView.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onClick(View v) {
        try {
            inputStream = getAssets().open("a.jpg");


            Bitmap bitmap = null;
            switch (v.getId()){
            case R.id.s:
                 bitmap = loadSampleImage(inputStream,5);
                Log.d(TAG, "小: ");
                break;
            case R.id.m:
                 bitmap = loadSampleImage(inputStream,3);
                Log.d(TAG, "中: ");
                break;

            case R.id.l:
                Log.d(TAG, "大: ");
                bitmap = loadSampleImage(inputStream,1);
                break;
            case R.id.src:
                Log.d(TAG, "原图: ");
                bitmap = loadSampleImage(inputStream,0);
                break;
            case R.id.part:
                bitmap = loadPartIamge(inputStream);


        }
          if (bitmap != null){
              Log.d(TAG, "bitmap.getByteCount():"+    bitmap.getByteCount());
              mImageView.setImageBitmap(bitmap);
          }else {
              Log.d(TAG, "bitmap is null: ");
          }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }





    /**
     *
     *     从输出流中选择  一个矩阵部分加载，加载一部分图片，避免OOM

     * @param inputStream
     */
    public  Bitmap  loadPartIamge(InputStream inputStream){
        try
        {

            //获得图片的宽、高
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            tmpOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, tmpOptions);
            int width = tmpOptions.outWidth;
            int height = tmpOptions.outHeight;

            //设置显示图片的中心区域
            BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = bitmapRegionDecoder.decodeRegion(new Rect(width / 2 - 200, height / 2 - 200, width / 2 + 200, height / 2 + 200), options);
            return  bitmap;


        } catch (IOException e)
        {
            e.printStackTrace();
            return  null;
        }

    }



    /**
     *
     *     根据传入的 宽高做压缩
     * @param inputStream
     * @param width
     * @param height
     * @return
     */
    public  Bitmap loadSampleImage(InputStream inputStream,int width,int height){
        BitmapFactory.Options options = new  BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 开启只加载图片的大小，不直接加载图片
        BitmapFactory.decodeStream(inputStream,null,options);//加载图片流的信息到options ，不指定 padding  默认上下左右为0
//        int outWidth  = options.outWidth;
//        int outHeight = options.outHeight;

        int ratio = calculateInSampleSize(options,width,height);

//                (int) ((1-Math.min((float)width/(float)outWidth,(float)height/(float)outHeight))*100);

        options.inSampleSize = ratio;
        //
        options.inJustDecodeBounds  = false; // 加载图片

        Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);
        bitmap = creatScaleBitmap(bitmap,width,height);
        return  bitmap;
    }

    /**
     *    从SD卡加载
     * @param path
     * @param width
     * @param height
     * @return
     */
    public  Bitmap loadSampleImage(String path,int width,int height){
        BitmapFactory.Options options = new  BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 开启只加载图片的大小，不直接加载图片
        BitmapFactory.decodeFile(path,options);//加载图片流的信息到options ，不指定 padding  默认上下左右为0
//        int outWidth  = options.outWidth;
//        int outHeight = options.outHeight;

        int ratio = calculateInSampleSize(options,width,height);

//                (int) ((1-Math.min((float)width/(float)outWidth,(float)height/(float)outHeight))*100);

        options.inSampleSize = ratio;
        //
        options.inJustDecodeBounds  = false; // 加载图片

        Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options); //得到稍大一点的 bitmap
        bitmap = creatScaleBitmap(bitmap,width,height);
        return  bitmap;
    }

    public  Bitmap creatScaleBitmap(Bitmap bitmap,int width,int height){
        return  Bitmap.createScaledBitmap(bitmap,width,height,false);
    }
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


    /**
     *      inSampleSize  图片进行压缩
     *      若inSampleSize  = n
     *      那么图片会得到 原图的  2的n次分之一
     *      但 如果 inSmapleSize  = 1 时会得到原始数据
     *      如：
     *          inSample   压缩
     *          1           1
     *          2           1/4
     *          3           1/8
     *          4           1/16
     * @param inputStream  输入流
     * @param ratio  压缩比
     * @return  bitmap
     */
    public  Bitmap  loadSampleImage(InputStream inputStream,int ratio){
        BitmapFactory.Options options = new  BitmapFactory.Options();
//        options.inJustDecodeBounds = true; // 开启只加载图片的大小，不直接加载图片
//        BitmapFactory.decodeStream(inputStream,null,options);//加载图片流的信息到options ，不指定 padding  默认上下左右为0
//        int width  = options.outWidth;
//        int height = options.outHeight;
        options.inSampleSize = ratio;
        //
        options.inJustDecodeBounds  = false;

        Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);
        return  bitmap;

    }

    /**
     *
     *
     * @param bitmap
     * @param quality  表示 压缩的后的质量 如  0 压缩100%，100意味着不压缩；
     * @return  压缩后得到的数据会被存进 OutputStream 可以用于输出到文件
     */
    public  OutputStream  loadCompressIamge(Bitmap bitmap,int quality){

        ByteArrayOutputStream ops = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,ops);
        return  ops;
    }
}
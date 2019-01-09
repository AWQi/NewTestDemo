package com.example.bigimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.ColorInt;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class ImageUtil {


static  public  int BYTES_PER_PIXEL = 1;
    public static byte[] getRgbaDataFromImage(Image image) {
        int width = image.getWidth();
        int height = image.getHeight();

        final Image.Plane[] planes = image.getPlanes();
        final ByteBuffer buffer = planes[0].getBuffer();
        int rowStride = planes[0].getRowStride();
        byte[] data = new byte[width * height * BYTES_PER_PIXEL];
        if (rowStride == width * BYTES_PER_PIXEL) {
            buffer.get(data); return data;
        }
        ByteBuffer destBuffer = ByteBuffer.wrap(data);
        byte[] buf = new byte[rowStride];
        int byteCount = width * BYTES_PER_PIXEL;
        for (int i = 0; i < height; i++) {
            buffer.get(buf);
            destBuffer.put(buf, 0, byteCount);
        } return data;
    }
    public static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }
    public static Bitmap bytes2Bitmap(final byte[] bytes) {
        return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    public static Bitmap drawable2Bitmap(final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        } Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    public static Drawable bitmap2Drawable(Context context,final Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(context.getResources(), bitmap);
    }

    public static byte[] drawable2Bytes(final Drawable drawable, final Bitmap.CompressFormat format) {
        return drawable == null ? null : bitmap2Bytes(drawable2Bitmap(drawable), format);
    }

    public static Drawable bytes2Drawable(Context context,final byte[] bytes) {
        return bitmap2Drawable(context,bytes2Bitmap(bytes));
    }

    public static Bitmap view2Bitmap(final View view) {
        if (view == null) return null;
        Bitmap ret = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

    public static Bitmap addTextWatermark(final Bitmap src, final String content, final float textSize, @ColorInt final int color, final float x, final float y, final boolean recycle) {
        if (src==null || content == null) return null;
        Bitmap ret = src.copy(src.getConfig(), true);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(ret); paint.setColor(color);
        paint.setTextSize(textSize); Rect bounds = new Rect();
        paint.getTextBounds(content, 0, content.length(), bounds);
        canvas.drawText(content, x, y + textSize, paint);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    public static Bitmap addImageWatermark(final Bitmap src, final Bitmap watermark, final int x, final int y, final int alpha, final boolean recycle) {
        if (src==null) return null;
        Bitmap ret = src.copy(src.getConfig(), true);
        if (watermark!=null) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            Canvas canvas = new Canvas(ret);
            paint.setAlpha(alpha);
            canvas.drawBitmap(watermark, x, y, paint);
        }
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    public static Bitmap toAlpha(final Bitmap src, final Boolean recycle) {
        if (src==null) return null;
        Bitmap ret = src.extractAlpha();
        if (recycle && !src.isRecycled()) src.recycle();
        return ret; }

    public static Bitmap toGray(final Bitmap src, final boolean recycle) {
        if (src==null) return null;
        Bitmap ret = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        Canvas canvas = new Canvas(ret);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixColorFilter);
        canvas.drawBitmap(src, 0, 0, paint);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }
    public static boolean isImage(final String filePath) {
        String path = filePath.toUpperCase();
        return path.endsWith(".PNG") || path.endsWith(".JPG")
                || path.endsWith(".JPEG") || path.endsWith(".BMP")
                || path.endsWith(".GIF") || path.endsWith(".WEBP"); }


}
